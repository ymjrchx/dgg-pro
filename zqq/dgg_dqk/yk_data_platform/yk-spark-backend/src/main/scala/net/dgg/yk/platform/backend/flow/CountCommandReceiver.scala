package net.dgg.yk.platform.backend.flow

import net.dgg.yk.platform.backend.command.{Command, CommandReceiver}

class CountCommandReceiver extends CommandReceiver {

  override def execute[T](command: Command): T = {
    val sc = command.getSc.sc
    val database: String = command.getParameter("database")
    val collection: String = command.getParameter("collection")
    val path = "/data/sink/" + database + "/" + collection + "/origin"

    val rdd = sc.objectFile[Object](path)

    println(s"database: ${database}\tcollection: ${collection}\tcount: ${rdd.count}")

    true.asInstanceOf[T]
  }

  override def getSupport: String = "count"

}
