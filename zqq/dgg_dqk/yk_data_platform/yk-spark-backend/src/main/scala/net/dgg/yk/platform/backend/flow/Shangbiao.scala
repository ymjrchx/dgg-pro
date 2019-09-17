package net.dgg.yk.platform.backend.flow

import java.lang.Integer.{valueOf => parseInt}
import java.time.Clock
import java.util

import com.google.gson.JsonElement
import com.mongodb.client.MongoCursor
import com.mongodb.client.model.Filters
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

class Shangbiao extends CommandReceiver {

  override def execute[T](command: Command): T = {

    val database = command.getParameter("database")
    val collection = command.getParameter("collection")
    val size = command.getParameter("size")

    val root: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("hdfs.root"))
    val mongoHost: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("mongo.host"))
    val mongoPort: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("mongo.port"))

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

    val mongoClient = MongoDao.getNormalMongoClient(hosts, ports)
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
            client = MongoDao.getNormalMongoClient(hosts, ports)
            val docs: util.List[Document] = new util.ArrayList[Document]
            cursor = if (conditions == null || conditions.length == 0) client.getDatabase(database).getCollection(collection).find.noCursorTimeout(true).iterator else client.getDatabase(database).getCollection(collection).find(Filters.and(conditions: _*)).noCursorTimeout(true).iterator
            while (cursor.hasNext) {
              val doc = cursor.next

              val regNo = doc.getString("regNo")
              val intcls = doc.getString("intcls")
              val agencyKeyNo = doc.getString("agencyKeyNo")

              def agency_results_new(agencyKeyNo: String): Array[Document] = {
                val coll = client.getDatabase("shangbiao_db").getCollection("agency_results_new")
                coll.find(new Document("agencyNum", agencyKeyNo)).iterator.asScala.toArray
              }

              doc.append("agency_results_new", agency_results_new(agencyKeyNo))

              def applicant_detail_results(intcls: String, regNum: String): Array[Document] = {
                val coll = client.getDatabase("shangbiao_db").getCollection("applicant_detail_results")
                coll.find(new Document("intcls", intcls).append("regNo", regNum)).iterator.asScala.toArray
              }

              doc.put("applicant_detail_results", applicant_detail_results(intcls, regNo))

              def collective_detail_results(regNo: String): Array[Document] = {
                val coll = client.getDatabase("shangbiao_db").getCollection("collective_detail_results")
                coll.find(new Document("regNo", regNo)).iterator.asScala.toArray
              }

              doc.put("collective_detail_results", collective_detail_results(regNo))

              def international_results_new(regNo: String): Array[Document] = {
                val coll = client.getDatabase("shangbiao_db").getCollection("international_results_new")
                coll.find(new Document("regNo", regNo)).iterator.asScala.toArray
              }

              doc.put("international_results_new", international_results_new(regNo))

              def priority_detail_results(intcls: String, regNo: String): Array[Document] = {
                val coll = client.getDatabase("shangbiao_db").getCollection("priority_detail_results")
                coll.find(new Document("intcls", intcls).append("regNo", regNo)).iterator.asScala.toArray
              }

              doc.put("priority_detail_results", priority_detail_results(intcls, regNo))

              def service_detail_results(intcls: String, regNo: String): Array[Document] = {
                val coll = client.getDatabase("shangbiao_db").getCollection("service_detail_results")
                coll.find(new Document("intcls", intcls).append("regNo", regNo)).iterator.asScala.toArray
              }

              doc.put("service_detail_results", service_detail_results(intcls, regNo))

              docs.add(doc)
            }
            CloseableUtils.closeQuietly(cursor)
            CloseableUtils.closeQuietly(client)
            returnObject = docs.iterator.asScala
            isReturn = true
            isContinue = false
          } catch {
            case e: Exception => {
              e.printStackTrace
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

  override def getSupport: String = "shangbiaoSink"
}
