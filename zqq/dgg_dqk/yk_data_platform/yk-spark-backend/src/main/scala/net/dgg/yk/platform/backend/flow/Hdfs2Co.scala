package net.dgg.yk.platform.backend.flow

import com.mongodb.BasicDBObject
import net.dgg.yk.common.Toolkit
import net.dgg.yk.platform.backend.business.a1.StandDetailData
import net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.QccCompany
import net.dgg.yk.platform.backend.command.Command
import net.dgg.yk.platform.backend.flow.delegates.Support._
import org.apache.spark.api.java.function.PairFunction
import org.apache.spark.rdd.RDD
import org.bson.{BSONObject, Document}
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark

class MongoDB2ESCom extends net.dgg.yk.platform.backend.command.CommandReceiver {

  override def execute[T](command: Command): T = {
    try {
      val root: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("hdfs.root"))
      val fsRoot: String = Toolkit.MapHelper.getDeepValue(command.getEnv, splitByDot("fs.root"))
      val index = command.getParameter("index")
      val `type` = command.getParameter("type")
      val database = command.getParameter("database")
      val collection = command.getParameter("collection")
      var originReadPath = root + database + "/" + collection + "/origin"
      val esWriteSource = index.concat("/").concat(`type`)
      val sc = command.getSc.sc
      val isLocal = sc.getConf.get("spark.master").contains("local")
      if (isLocal) {
        originReadPath = fsRoot + "/" + database + "/" + collection + "/origin"
      }
      val docs = sc.objectFile[Object](originReadPath).map(doc => {
        if (doc.isInstanceOf[Document]) {
          BasicDBObject.parse(doc.asInstanceOf[Document].toJson())
        } else if (doc.isInstanceOf[BasicDBObject]) {
          doc.asInstanceOf[BasicDBObject]
        } else if (doc.isInstanceOf[BSONObject]) {
          doc.asInstanceOf[BSONObject]
        } else {
          throw new IllegalStateException("不支持的类型")
        }
      })

      println(s"分区数量：${docs.getNumPartitions}")
      var convertedData: RDD[QccCompany] = null
      if (isLocal) {
        convertedData = docs.map(StandDetailData.conver)
        convertedData.take(10).foreach(println)
        JavaEsSpark.saveToEsWithMeta(convertedData.toJavaRDD().mapToPair(
          new PairFunction[QccCompany, String, QccCompany] {
            override def call(company: QccCompany): (String, QccCompany) = {
              def foo(map: QccCompany): (String, QccCompany) = {
                (map.getCompanyId, map)
              }

              foo(company)
            }
          }
        ), esWriteSource
        )
      } else {
        convertedData = docs.map(StandDetailData.conver)
        JavaEsSpark.saveToEsWithMeta(convertedData.toJavaRDD().mapToPair(
          new PairFunction[QccCompany, String, QccCompany] {
            override def call(company: QccCompany): (String, QccCompany) = {
              implicit val company_a: QccCompany = company

              def foo(implicit map: QccCompany): (String, QccCompany) = {
                (map.getCompanyId, map)
              }

              foo
            }
          }
        ), esWriteSource
        )
      }

      true.asInstanceOf[T]
    } catch {
      case e: Exception =>
        e.printStackTrace()
        false.asInstanceOf[T]
    }
    true.asInstanceOf[T]
  }

  override def getSupport: String = "2c"

}
