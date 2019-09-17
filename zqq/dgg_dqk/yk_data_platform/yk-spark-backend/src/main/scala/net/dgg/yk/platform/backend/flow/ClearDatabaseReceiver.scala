package net.dgg.yk.platform.backend.flow

import net.dgg.yk.platform.backend.command.Command
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import net.dgg.yk.platform.backend.command.CommandReceiver

class ClearDatabaseReceiver extends CommandReceiver {

  override def execute[T](command: Command): T = {
    try {
      val configuration = new Configuration
      val fs = FileSystem.get(configuration)
      val root: String = command.getEnvParameter("hdfs.root")
      val database = command.getParameter("database")
      val collection = command.getParameter("collection")
      val originReadPath = root + database + "/" + collection + "/origin"
      fs.delete(new Path(originReadPath), true)
      true.asInstanceOf[T]
    } catch {
      case e: Exception => {
        e.printStackTrace()
        false.asInstanceOf[T]
      }
    }
    true.asInstanceOf[T]
  }

  override def getSupport: String = "clear"

}
