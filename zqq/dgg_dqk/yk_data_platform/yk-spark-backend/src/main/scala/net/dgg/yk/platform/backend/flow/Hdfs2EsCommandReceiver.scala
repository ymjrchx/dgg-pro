package net.dgg.yk.platform.backend.flow

import java.nio.charset.Charset
import java.util

import com.google.gson.{JsonArray, JsonObject}
import com.mongodb.BasicDBObject
import net.dgg.yk.common.Toolkit
import net.dgg.yk.platform.backend.command.{Command, CommandReceiver}
import net.dgg.yk.platform.backend.flow.delegates.Support.{copy, splitByDot}
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.spark.api.java.function.PairFunction
import org.apache.spark.rdd.RDD
import org.bson.Document
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark

class Hdfs2EsCommandReceiver extends CommandReceiver {

  override def execute[T](command: Command): T = {
    val sc = command.getSc.sc
    val root: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("hdfs.root"))
    val localRoot: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("fs.root"))
    val isLocal = sc.getConf.get("spark.master").contains("local")
    val index: String = command.getParameter("index")
    val `type` = command.getParameter("type")
    val idField = command.getParameter("idField")
    val database = command.getParameter("database")
    val collection = command.getParameter("collection")
    val taskSet = command.getParameter("taskSet")
    val taskConfig = command.getParameter("taskConfig")
    val esWriteSource = index.concat("/").concat(`type`)
    val path = if (isLocal) localRoot else root
    val originReadPath = s"${path}${database}/${collection}/origin"

    val taskSetArr: String = if (StringUtils.isEmpty(taskConfig)) taskSet else IOUtils.toString(this.getClass.getResourceAsStream(taskConfig), Charset.forName("UTF-8"))

    println(s"执行任务，数据集：${originReadPath}")
    val origin: RDD[AnyRef] = sc.objectFile(originReadPath)
    val convertedData: RDD[util.Map[String, AnyRef]] = origin.map((doc: AnyRef) => {
      def foo(doc: AnyRef) = {
        var root: JsonObject = null
        if (doc.isInstanceOf[BasicDBObject]) root = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[BasicDBObject].toJson)
        else if (doc.isInstanceOf[Document]) root = Toolkit.JSON.parseJsonObject(doc.asInstanceOf[Document].toJson)
        else throw new IllegalStateException("无法支持的类型")
        val taskSet: JsonArray = Toolkit.JSON.parseJsonArray(taskSetArr)
        copy(root, taskSet)
      }

      foo(doc)
    })

    JavaEsSpark.saveToEsWithMeta(convertedData.map(
      (map: util.Map[String, AnyRef]) => new Tuple2[String, util.Map[String, AnyRef]](Toolkit.MapHelper.getDeepValue(map, splitByDot(idField)).asInstanceOf[String], map)
    ).toJavaRDD().mapToPair(new PairFunction[(String, util.Map[String, AnyRef]), String, util.Map[String, AnyRef]] {
      override def call(twoMap: (String, util.Map[String, AnyRef])): (String, util.Map[String, AnyRef]) = {
        twoMap
      }
    }), esWriteSource)

    true.asInstanceOf[T]
  }

  override def getSupport() = "h2e"

}
