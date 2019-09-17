package net.dgg.yk.platform.backend.flow

import net.dgg.yk.platform.backend.command.{Command, CommandReceiver}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.bson.Document

class DistinctSaveReceiver extends CommandReceiver {

  override def execute[T](command: Command): T = {
    val sc = command.getSc.sc
    val local = sc.getConf.get("spark.master").contains("local")

    val root: String = command.getEnvParameter("hdfs.root")
    val localRoot: String = command.getEnvParameter("fs.root")

    val database = command.getParameter("database")
    val collection = command.getParameter("collection")
    val originReadPath = root + database + "/" + collection + "/origin"

    val targetRoot = command.getParameter("target")
    val target = targetRoot + database + "/" + collection + "/origin"

    val distinctBefore = sc.objectFile[Document](originReadPath)
    val idDoc = distinctBefore.map(doc => (doc.getString("_id"), doc))
    val distinctedDoc = idDoc.reduceByKey {
      case (doc1, doc2) => doc1
    } map {
      case (id, doc) => doc
    }

    if (!local) {
      val configuration = new Configuration
      val fs = FileSystem.get(configuration)
      if (fs.exists(new Path(target))) {
        fs.delete(new Path(target), true)
      }
    }
    distinctedDoc.saveAsObjectFile(target)
    true.asInstanceOf[T]
  }

  override def getSupport: String = "distinctBusiness"

}
