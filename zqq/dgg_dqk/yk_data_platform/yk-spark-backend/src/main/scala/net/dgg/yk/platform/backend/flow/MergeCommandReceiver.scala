package net.dgg.yk.platform.backend.flow

import java.nio.charset.Charset
import java.util

import com.google.gson.{JsonElement, JsonObject}
import com.mongodb.BasicDBObject
import net.dgg.yk.common.Toolkit
import net.dgg.yk.platform.backend.command.{Command, CommandReceiver}
import net.dgg.yk.platform.backend.flow.delegates.Support
import net.dgg.yk.platform.backend.flow.delegates.Support.{copy, splitByDot}
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.bson.Document
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark

import scala.collection.JavaConverters._

class MergeCommandReceiver extends CommandReceiver {

  override def execute[T](command: Command): T = {
    val root: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("hdfs.root"))
    val index = command.getParameter("index")
    val `type` = command.getParameter("type")

    val database = command.getParameter("database")
    val collection = command.getParameter("collection")

    val database1 = command.getParameter("database1")
    val collection1 = command.getParameter("collection1")

    val esWriteSource = index.concat("/").concat(`type`)

    val originReadPath = root + database + "/" + collection + "/origin"
    val originReadPath1 = root + database1 + "/" + collection1 + "/origin"

    val taskConfig = command.getParameter("taskConfig")
    val taskSet = command.getParameter("taskSet")

    val sc = command.getSc.sc
    val taskSetArr = if (StringUtils.isEmpty(taskConfig)) taskSet
    else IOUtils.toString(classOf[MergeCommandReceiver].getResourceAsStream(taskConfig), Charset.forName("UTF-8"))
    val isLocal = sc.getConf.get("spark.master").contains("local")

    val path = if (isLocal) "file:///e:/database" + originReadPath else originReadPath
    val targetPath = if (isLocal) "file:///e:/database" + originReadPath1 else originReadPath1

    val target: RDD[Object] = sc.objectFile(targetPath)
    val targetTels = rawToTelRDD(target).persist(StorageLevel.MEMORY_ONLY_SER)

    val origin: RDD[AnyRef] = sc.objectFile(path)
    val convertedData = rawToEs(origin, taskSetArr)
    val primaryRDD = convertedData.map((m: util.Map[String, AnyRef]) => {
      def foo(m: util.Map[String, AnyRef]) = (m.get("companyId").asInstanceOf[String], m)

      foo(m)
    }).persist(StorageLevel.MEMORY_AND_DISK)

    val joined = primaryRDD.leftOuterJoin(targetTels)
    val esRDD = joined.mapValues {
      case (es: util.Map[String, AnyRef], tel: Option[util.LinkedList[String]]) => {
        def foo(primary: util.Map[String, AnyRef], list: Option[util.LinkedList[String]]) = {
          val target_tels = if (list.nonEmpty) list.get else new util.LinkedList[String]
          val originTel: AnyRef = Toolkit.MapHelper.getDeepValue(primary, Support.splitByDot("commercial.companyTel"))
          val ori_tels = MergeCommandReceiver.attachTelephone(originTel)
          val allTels = new util.LinkedList[String]
          allTels.addAll(ori_tels)
          allTels.addAll(target_tels)
          val distinct_tels = allTels.asScala.distinct.asJava
          val finalTels = new util.LinkedList[String]
          finalTels.addAll(distinct_tels)
          if (!ori_tels.isEmpty) {
            val first = ori_tels.getFirst
            finalTels.remove(first)
            finalTels.addFirst(first)
          }
          val mapped = new util.LinkedHashMap[String, AnyRef]
          mapped.putAll(primary)
          val telStr = new StringBuilder
          var i = 0
          while (i < finalTels.size) {
            if (i > 0) telStr.append(",")
            telStr.append(finalTels.get(i))
            i += 1;
          }
          Toolkit.MapHelper.putDeepValue(mapped, Support.splitByDot("commercial.companyTel"), telStr.toString)
          mapped
        }

        foo(es, tel)
      }
    }

    if (isLocal) esRDD.take(10).foreach(out => println(out))
    else JavaEsSpark.saveToEsWithMeta(esRDD.toJavaRDD().mapToPair(
      new org.apache.spark.api.java.function.PairFunction[(String, util.LinkedHashMap[String, AnyRef]), String, util.LinkedHashMap[String, AnyRef]]() {
        override def call(t: (String, util.LinkedHashMap[String, AnyRef])): (String, util.LinkedHashMap[String, AnyRef]) = {
          (t._1, t._2)
        }
      }
    ), esWriteSource
    )
    true.asInstanceOf[T]
  }

  override def getSupport: String = "merge"


  def rawToEs(raws: RDD[AnyRef], tasks: String): RDD[util.Map[String, AnyRef]] = {
    val mapped = raws.map((doc: Any) => {
      def foo(doc: Any) = {
        var croot: JsonObject = null
        if (doc.isInstanceOf[String]) croot = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[String])
        else if (doc.isInstanceOf[Document]) croot = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[Document].toJson)
        else if (doc.isInstanceOf[BasicDBObject]) croot = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[BasicDBObject].toJson)
        else throw new IllegalStateException("无法支持的类型")
        val taskArray = Toolkit.JSON.parseJsonArray(tasks)
        copy(croot, taskArray)
      }

      foo(doc)
    })
    mapped
  }

  def rawToTelRDD(raws: RDD[AnyRef]): RDD[(String, util.LinkedList[String])] = {
    val mapped = raws.map((doc: Any) => {
      def foo(doc: Any) = {
        var croot: JsonObject = null
        if (doc.isInstanceOf[String]) croot = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[String])
        else if (doc.isInstanceOf[Document]) croot = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[Document].toJson)
        else if (doc.isInstanceOf[BasicDBObject]) croot = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[BasicDBObject].toJson)
        else throw new IllegalStateException("无法支持的类型")
        val telephones = MergeCommandReceiver.getTelephone(croot)
        (croot.get("_id").getAsString, telephones)
      }

      foo(doc)
    })
    mapped
  }

}

object MergeCommandReceiver {

  def attachTelephone(tel: AnyRef): util.LinkedList[String] = {
    var returnObject: util.LinkedList[String] = null
    var isReturn = false
    if (tel == null) {
      returnObject = new util.LinkedList[String]
      isReturn = true
    } else if (tel.isInstanceOf[String]) {
      val telStr = tel.asInstanceOf[String]
      val splits = telStr.split(",")
      val ss = new util.LinkedList[String]
      var i = 0
      while (i < splits.length) {
        ss.add(splits(i))
        i += 1
      }
      returnObject = ss
      isReturn = true
    } else if (tel.isInstanceOf[util.List[_]]) {
      val ls = tel.asInstanceOf[util.List[_]]
      val ss = new util.LinkedList[String]
      var i = 0
      while (i < ls.size) {
        val s = ls.get(i)
        if (s == null) {

        } else if (s.isInstanceOf[String]) {
          ss.add(s.asInstanceOf[String])
        } else if (s.isInstanceOf[Number]) {
          ss.add(String.valueOf(s.asInstanceOf[Number]))
        } else {
          throw new IllegalStateException("不支持类型：java.util.List<" + s.getClass.getName + ">")
        }
        i += 1
      }
      returnObject = ss
      isReturn = true
    }
    if (isReturn) {
      returnObject
    } else {
      throw new IllegalStateException("无法识别的类型：" + tel.getClass.getName)
    }
  }

  val getTelephone: JsonObject => util.LinkedList[String] = (context: JsonObject) => {
    val reportInfo: JsonElement = Toolkit.JSON.getDeepValueOrNull(context, Support.splitByDot("docs.background.reportInfo"), classOf[Any]).asInstanceOf[JsonElement]
    val telElement: JsonElement = Toolkit.JSON.getDeepValueOrNull(context, Support.splitByDot("docs.background.baseInfo.companyTel"), classOf[Any]).asInstanceOf[JsonElement]
    val telArr: util.LinkedList[String] = new util.LinkedList[String]
    if (telElement != null && !telElement.isJsonNull) if (telElement.isJsonPrimitive) telArr.addLast(telElement.getAsString)
    else if (telElement.isJsonArray) {
      val iterator: util.Iterator[JsonElement] = telElement.getAsJsonArray.iterator
      while ( {
        iterator.hasNext
      }) {
        val ele: JsonElement = iterator.next
        if (ele != null && ele.isJsonPrimitive) telArr.addLast(ele.getAsString)
      }
    }
    val rTels: util.LinkedList[String] = new util.LinkedList[String]
    if (reportInfo != null && reportInfo.isJsonObject) {
      val entries: util.Iterator[util.Map.Entry[String, JsonElement]] = reportInfo.getAsJsonObject.entrySet.iterator
      while (entries.hasNext) {
        val entry: util.Map.Entry[String, JsonElement] = entries.next
        val `val`: JsonElement = entry.getValue
        if (`val` != null && `val`.isJsonObject) {
          val rTel: String = Toolkit.JSON.getDeepValueOrNull(`val`.getAsJsonObject, Support.splitByDot("rBaseInfo.rTel"), classOf[String])
          if (StringUtils.isNotEmpty(rTel)) rTels.addLast(rTel)
        }
      }
    }
    rTels.addAll(telArr)
    val list: util.List[String] = rTels.asScala.distinct.asJava
    val tels: util.LinkedList[String] = new util.LinkedList[String]
    tels.addAll(list)
    if (!telArr.isEmpty) {
      val first: String = telArr.getFirst
      tels.remove(first)
      tels.addFirst(first)
    }

    val finalTels: util.List[String] = tels.asScala.map((telephoneNo: String) => {
      implicit val telephone: String = telephoneNo

      def foo(implicit tel: String) = if (tel != null) {
        var processChainObject: String = tel.replaceAll("[^\\d]+", "")
        if (processChainObject.length > 11) processChainObject = processChainObject.substring(processChainObject.length - 11)
        processChainObject
      } else tel

      foo
    }).distinct.asJava

    val finalTelephones: util.LinkedList[String] = new util.LinkedList[String]
    finalTelephones.addAll(finalTels)
    finalTelephones
  }
}