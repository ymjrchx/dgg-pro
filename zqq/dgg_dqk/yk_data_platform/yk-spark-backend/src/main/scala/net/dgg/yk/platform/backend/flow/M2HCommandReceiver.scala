package net.dgg.yk.platform.backend.flow

import java.lang.Integer.{valueOf => parseInt}
import java.time.Clock
import java.util

import com.google.gson.JsonElement
import com.mongodb.client.model.Filters
import com.mongodb.client.{MongoCollection, MongoCursor}
import com.mongodb.{BasicDBObject, MongoClient}
import net.dgg.yk.common.Toolkit
import net.dgg.yk.platform.backend.command.{Command, CommandReceiver}
import net.dgg.yk.platform.backend.common.MongoDao
import net.dgg.yk.platform.backend.flow.delegates.Support.splitByDot
import org.apache.commons.lang3.StringUtils
import org.apache.curator.utils.CloseableUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.bson.Document
import org.bson.conversions.Bson

import scala.collection.JavaConverters._

class M2HCommandReceiver extends CommandReceiver {

  override def execute[T](command: Command): T = {

    val database = command.getParameter("database")
    val collection = command.getParameter("collection")
    val size = command.getParameter("size")

    val localRoot: String = command.getEnvParameter("fs.root")
    val root: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("hdfs.root"))
    val mongoHost: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("mongo.host"))
    val mongoPort: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("mongo.port"))
    val mongoUsername: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("mongo.username"))
    val mongoPassword: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("mongo.password"))

    val refDatabase = command.getParameter("refDatabase")
    val refCollection = command.getParameter("refCollection")
    val refProjectionObject: JsonElement = Toolkit.JSON.getDeepValueOrNull(command.getCommandOrigin, splitByDot("refProjection"), classOf[Any]).asInstanceOf[JsonElement]
    val refProjection: String = if (refProjectionObject != null && refProjectionObject.isJsonObject) refProjectionObject.toString else null
    val refField = command.getParameter("refField")

    val originReadPath = root + database + "/" + collection + "/origin"
    val ports = mongoPort.split(",").map(parseInt(_))
    val hosts = mongoHost.split(",")

    val query: JsonElement = Toolkit.JSON.getDeepValueOrNull(command.getCommandOrigin, splitByDot("query"), classOf[Any]).asInstanceOf[JsonElement]
    val statement = if (query == null) null else query.toString

    val sc = command.getSc.sc
    val isLocal = sc.getConf.get("spark.master").contains("local")

    val boundaryCount = if (StringUtils.isEmpty(size)) 1000 else size.toInt

    val partitionKey = "_id"
    val projection = new Document(partitionKey, 1)
    val sort = new BasicDBObject(partitionKey, 1)

    val mongoClient = MongoDao.getNormalMongoClient(hosts, ports, mongoUsername, mongoPassword)
    val mongoDatabase = mongoClient.getDatabase(database)
    val mongoCollection = mongoDatabase.getCollection(collection)

    var isContinue = true
    var prevId = ""

    println(s"正在数据库：${database}\t集合：${collection} 搜索分区")

    val boundaries: util.LinkedList[String] = new util.LinkedList[String]
    var count: Int = 0
    val start: Long = Clock.systemUTC.millis
    while (isContinue) {
      var cursor: MongoCursor[Document] = null
      if (StringUtils.isEmpty(prevId)) {
        cursor = if (StringUtils.isEmpty(statement)) mongoCollection.find.projection(projection).sort(sort).skip(boundaryCount).limit(1).iterator else mongoCollection.find(Document.parse(statement)).projection(projection).sort(sort).skip(boundaryCount).limit(1).iterator
      } else {
        val condition = if (StringUtils.isEmpty(statement)) Filters.gt(partitionKey, prevId) else Filters.and(Filters.gt(partitionKey, prevId), Document.parse(statement))
        cursor = mongoCollection.find(condition).projection(projection).sort(sort).skip(boundaryCount).limit(1).iterator
      }
      if (cursor.hasNext) {
        val doc: Document = cursor.next
        prevId = doc.getString(partitionKey)
        count += 1
        boundaries.add(prevId)
        isContinue = true
        println(s"发现第${count}个分区边界${prevId}")
      }
      else isContinue = false
      CloseableUtils.closeQuietly(cursor)
    }
    CloseableUtils.closeQuietly(mongoClient)

    println(s"耗时：${Clock.systemUTC().millis() - start}")
    println(s"数据库：${database}\t集合：${collection}\t分区数量：${count + 1}")
    println()

    val rangeList = new util.ArrayList[(String, String)]

    var prevBoundary: String = ""
    var nextBoundary: String = ""
    for (i <- 0 until boundaries.size) {
      nextBoundary = boundaries.get(i)
      rangeList.add((prevBoundary, nextBoundary))
      prevBoundary = nextBoundary
    }
    rangeList.add((prevBoundary, ""))

    val rangeRDD = sc.makeRDD(rangeList.asScala, rangeList.size)

    val refHosts = command.getParameter("refHosts")
    val refPorts = command.getParameter("refPorts")
    val refUsername = command.getParameter("refUsername")
    val refPwd = command.getParameter("refPwd")

    val mongoRDD = rangeRDD.flatMap {
      case (left: String, right: String) => {
        var reTry: Int = 0
        var isContinue: Boolean = true
        var isReturn: Boolean = false
        var returnObject: Iterator[Document] = null
        while (reTry < 20 && isContinue) {
          reTry += 1
          var client: MongoClient = null
          var cursor: MongoCursor[Document] = null
          try {
            val condition: util.List[Bson] = new util.ArrayList[Bson]
            if (StringUtils.isNotEmpty(left)) condition.add(Filters.gte(partitionKey, left))
            if (StringUtils.isNotEmpty(right)) condition.add(Filters.lt(partitionKey, right))
            if (StringUtils.isNotEmpty(statement)) condition.add(BasicDBObject.parse(statement))
            val conditions: Array[Bson] = condition.toArray(new Array[Bson](0))
            client = MongoDao.getNormalMongoClient(hosts, ports, mongoUsername, mongoPassword)
            val docs: util.List[Document] = new util.ArrayList[Document]
            cursor = if (conditions == null || conditions.length == 0) client.getDatabase(database).getCollection(collection).find.noCursorTimeout(true).iterator else client.getDatabase(database).getCollection(collection).find(Filters.and(conditions: _*)).noCursorTimeout(true).iterator
            if (StringUtils.isNotEmpty(refDatabase)) {
              var cli: MongoClient = null
              try {
                cli = MongoDao.getNormalMongoClient(refHosts.split("\\,"), refPorts.split("\\,").map(Integer.valueOf), refUsername, refPwd)
                val refDB = cli.getDatabase(refDatabase)
                val refC: MongoCollection[Document] = refDB.getCollection(refCollection)
                while (cursor.hasNext) {
                  val doc = cursor.next
                  val id = doc.getString("_id")
                  var refDoc: Document = null
                  if (StringUtils.isEmpty(refProjection)) {
                    refDoc = refC.find(Filters.eq("_id", id)).first()
                  } else {
                    refDoc = refC.find(Filters.eq("_id", id)).projection(Document.parse(refProjection)).first()
                  }
                  if (refDoc != null) {
                    doc.put(refField, refDoc)
                  }
                  docs.add(doc)
                }
              } catch {
                case _ => {
                  if (cli != null) {
                    CloseableUtils.closeQuietly(cli)
                  }
                }
              }
            } else {
              while (cursor.hasNext) {
                val doc = cursor.next
                docs.add(doc)
              }
            }
            CloseableUtils.closeQuietly(cursor)
            CloseableUtils.closeQuietly(client)
            returnObject = docs.iterator.asScala
            isReturn = true
            isContinue = false
          } catch {
            case _ => {
              if (client != null) CloseableUtils.closeQuietly(client)
              if (cursor != null) CloseableUtils.closeQuietly(cursor)
              isContinue = true
            }
          }
        }
        if (isReturn) {
          returnObject
        } else {
          throw new IllegalStateException("发生错误")
        }
      }
    }

    if (isLocal) {
      val count = mongoRDD.count
      val samples = mongoRDD.take(10)
      println(count)
      samples.foreach(println)
    } else {
      val hadoopConfig: Configuration = new Configuration
      val fs: FileSystem = FileSystem.get(hadoopConfig)
      fs.delete(new Path(originReadPath), true)
      mongoRDD.saveAsObjectFile(originReadPath)
    }
    true.asInstanceOf[T]
  }

  override def getSupport: String = "m2h"

}
