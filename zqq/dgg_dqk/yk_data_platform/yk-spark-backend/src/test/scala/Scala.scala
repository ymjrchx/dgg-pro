import java.io.OutputStream

import akka.actor._
import net.dgg.yk.common.Toolkit.StringHelper

import scala.reflect.io.{File, Path => FPath}
import scala.tools.nsc.interpreter.InputStream
import scala.util.Try

object ScalaDelegate {

  def apply(arr: Array[String]): ScalaDelegate = new ScalaDelegate()

  def main(args: Array[String]): Unit = {
    val systemActor = ActorSystem("root")
    val schedule = systemActor.actorOf(Props(new Schedule))
    schedule ! StartTask("E:\\BaiduNetdiskDownload\\1\\HDP-UTILS-1.1.0.21-centos7.tar.gz", "E:\\BaiduNetdiskDownload\\1\\1001.tar.gz")
  }

}

class ScalaDelegate() {
  def apply(arr: Array[String]): Unit = arr foreach println
}

class Scala {

}

case class StartTask(in: String, out: String)

case class StopTask(in: String, out: String)


class Schedule extends Actor {

  val taskMap: java.util.HashMap[String, ActorRef] = new java.util.HashMap[String, ActorRef]

  override def receive: Receive = {

    case StartTask(in, out) => {
      val id = StringHelper.md5(in + out)
      val actor = context.actorOf(Props(new Execute(id, in, out)), id)
      taskMap.put(id, actor)
      actor ! "start"
    }

    case StopTask(in, out) => {
      val id = StringHelper.md5(in + out)
      taskMap.remove(id)
    }

    case (id: String, "ask") => {
      if (taskMap.containsKey(id)) {
        sender ! "step"
      } else {
        sender ! "stop"
      }
    }
    case (id: String, "complete") => {
      context.system.terminate()
      taskMap.remove(id)
    }
    case (id: String, "stopped") => {
      context.system.terminate()
      taskMap.remove(id)
    }
  }
}

class Execute(val id: String, val in: String, val output: String) extends Actor {

  val inputFile: File = File(FPath(in))
  val outputFile: File = File(FPath(output))
  var inputStream: InputStream = null
  var outputStream: OutputStream = null
  val buff = Array.ofDim[Byte](10 * 1024 * 1024)

  override def receive: Receive = {
    case "start" => {
      inputStream = inputFile.inputStream
      outputStream = outputFile.outputStream(false)
      sender ! (id, "ask")
    }

    case "step" => {
      val count = inputStream.read(buff)
      if (count > 0) {
        outputStream.write(buff, 0, count)
        outputStream.flush
        sender ! (id, "ask")
      } else if (count == -1) {
        sender ! (id, "complete")
        close
      }
    }

    case "stop" => {
      sender ! (id, "stopped")
      close
    }
  }

  def close(): Unit = {
    if (inputStream != null) {
      Try(inputStream.close)
    }
    if (outputStream != null) {
      Try(outputStream.close)
    }
    context stop self
  }
}