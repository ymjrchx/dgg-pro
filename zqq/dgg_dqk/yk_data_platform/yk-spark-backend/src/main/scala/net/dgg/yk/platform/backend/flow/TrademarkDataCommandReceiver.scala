package net.dgg.yk.platform.backend.flow

import net.dgg.yk.platform.backend.business.a6.dependencies.SbDetails
import net.dgg.yk.platform.backend.command.{Command, CommandReceiver}
import org.apache.spark.api.java.function.PairFunction
import org.bson.Document
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark

class TrademarkDataCommandReceiver extends CommandReceiver {

  override def getSupport: String = "h2t"

  override def execute[T](command: Command): T = {
    val sc = command.getSc.sc
    val database: String = command.getParameter("database")
    val collection: String = command.getParameter("collection")
    val root = command.getEnvParameter[String]("hdfs.root")
    val fs = command.getEnvParameter[String]("fs.root")
    val isLocal = sc.getConf.get("spark.master").contains("local")
    val rootPath = if (isLocal) fs else root
    val path = s"${rootPath}${database}/${collection}/origin"
    val index = command.getParameter("index")
    val `type` = command.getParameter("type")
    val esWriteSource = s"${index}/${`type`}"
    val rdd = sc.objectFile[Document](path)
    val convertedData = rdd.map(net.dgg.yk.platform.backend.business.a6.BrandDetailData.convert)
    JavaEsSpark.saveToEsWithMeta(convertedData.toJavaRDD().mapToPair(
      new PairFunction[SbDetails, String, SbDetails] {
        override def call(detail: SbDetails): (String, SbDetails) = {
          implicit val detail_a = detail

          def foo(implicit map: SbDetails): (String, SbDetails) = {
            (map.getId, map)
          }

          foo
        }
      }
    ), esWriteSource
    )
    true.asInstanceOf[T]
  }

}