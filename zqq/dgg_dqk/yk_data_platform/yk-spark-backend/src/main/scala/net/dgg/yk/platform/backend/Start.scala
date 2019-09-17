package net.dgg.yk.platform.backend

import java.io.{ByteArrayOutputStream, PrintStream}
import java.util
import java.util.concurrent.ArrayBlockingQueue
import java.util.function

import akka.actor._
import com.google.gson.JsonObject
import com.rabbitmq.client.Channel
import net.dgg.yk.common.Toolkit
import net.dgg.yk.platform.backend.command.{Command, CommandFactory, CommandReceiver}
import net.dgg.yk.platform.backend.flow.delegates.Support.splitByDot
import net.dgg.yk.platform.backend.rabbitmq.RabbitMQAgent
import org.apache.commons.lang.StringUtils
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.SparkSession
import org.reflections.Reflections
import org.yaml.snakeyaml.Yaml

import scala.util.Try

object Start {

  val mainBlocking = new ArrayBlockingQueue[Boolean](1)

  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(Class.forName("net.dgg.yk.platform.backend.StartActor").getName()))
    mainBlocking.take
  }
}

case class Stop() {

}

case class Start() {

}

class StartActor extends Actor {

  val channels = Array.ofDim[(String, Channel)](3)

  System.setProperty("spark.mongodb.keep_alive_ms", "100000000")

  override def preStart(): Unit = {
    self ! Start()
  }

  override def receive: Receive = {
    case Stop() => {
      channels.foreach {
        case (_, channel) => {
          try {
            channel.getConnection.close
          } catch {
            case _ => {

            }
          }
        }
      }
      context.system terminate()
      context stop self
      Start.mainBlocking.offer(true)
    }
    case Start() => {
      val conf: SparkConf = new SparkConf
      if (StringUtils.isNotEmpty(System.getProperty("spark.master"))) conf.set("spark.master", System.getProperty("spark.master"))
      conf.set("spark.shuffle.service.enabled", "true")
      conf.set("spark.dynamicAllocation.enabled", "true")
      val yaml: Yaml = new Yaml

      val inputStream = this.getClass.getResourceAsStream("/application10.0.0.120.yml")
      val mapCls = classOf[util.Map[String, AnyRef]]
      val config = yaml.loadAs(inputStream, mapCls)
      import collection.JavaConverters._
      val reflections: Reflections = new Reflections("net.dgg.yk.platform.backend")

      val receivers: util.Set[CommandReceiver] = reflections.getSubTypesOf(classOf[CommandReceiver]).asScala.map(
        clazz => {
          def foo(receiver: Class[_]) = {
            try {
              receiver.asInstanceOf[Class[_]].newInstance.asInstanceOf[CommandReceiver]
            } catch {
              case e: Exception =>
                throw new IllegalStateException(e)
            }
          }

          foo(clazz)
        }
      ).asJava

      val appName: String = if (StringUtils.isNotEmpty(System.getProperty("app.name"))) System.getProperty("app.name") else Toolkit.MapHelper.getDeepValue(config, splitByDot("app.name"))

      val esServers: String = if (StringUtils.isNotEmpty(System.getProperty("elasticsearch.servers"))) System.getProperty("elasticsearch.servers") else Toolkit.MapHelper.getDeepValue(config, splitByDot("elasticsearch.servers"))
      val mqHost: String = if (StringUtils.isNotEmpty(System.getProperty("mmq.host"))) System.getProperty("mq.host") else Toolkit.MapHelper.getDeepValue(config, splitByDot("mq.host"))
      val mqPort: Integer = if (StringUtils.isNotEmpty(System.getProperty("mq.port"))) Integer.valueOf(System.getProperty("mq.port")) else Toolkit.MapHelper.getDeepValue(config, splitByDot("mq.port"))
      val mqUsername: String = if (StringUtils.isNotEmpty(System.getProperty("mq.username"))) System.getProperty("mq.username") else Toolkit.MapHelper.getDeepValue(config, splitByDot("mq.username"))
      val mqPassword: String = if (StringUtils.isNotEmpty(System.getProperty("mq.password"))) System.getProperty("mq.password") else Toolkit.MapHelper.getDeepValue(config, splitByDot("mq.password"))
      val mqVHost: String = if (StringUtils.isNotEmpty(System.getProperty("mq.vHost"))) System.getProperty("mmq.vHost") else Toolkit.MapHelper.getDeepValue(config, splitByDot("mq.vHost"))

      val rabbitMQAgent: RabbitMQAgent = RabbitMQAgent.builder.user(mqUsername).password(mqPassword).host(mqHost).port(mqPort).vHost(mqVHost).build

      val actionQueue: String = if (StringUtils.isNotEmpty(System.getProperty("mq.queue.action"))) System.getProperty("mq.queue.action") else Toolkit.MapHelper.getDeepValue(config, splitByDot("mq.queue.action"))
      val failureQueue: String = if (StringUtils.isNotEmpty(System.getProperty("mq.queue.failure"))) System.getProperty("mq.queue.failure") else Toolkit.MapHelper.getDeepValue(config, splitByDot("mq.queue.failure"))

      conf.setAppName(appName)

      conf.set("es.index.auto.create", "true").set("es.nodes", esServers).set("es.nodes.wan.only", "true").set("es.batch.size.entries", "500")

      val session: SparkSession = SparkSession.builder.config(conf).getOrCreate
      val sc: JavaSparkContext = new JavaSparkContext(session.sparkContext)
      val messages = new ArrayBlockingQueue[String](1000)

      channels(0) = rabbitMQAgent.declareQueue(actionQueue, true, false, false, null)
      channels(1) = rabbitMQAgent.declareQueue(failureQueue, true, false, false, null)
      println(String.format(s"正在监听：${actionQueue}，接收命令"))

      channels(2) = rabbitMQAgent.consumeText(actionQueue, 1,
        new function.Function[java.lang.String, java.lang.Boolean] {
          override def apply(t: java.lang.String): java.lang.Boolean = {
            def foo(body: String) = {
              messages.offer(body)
              true
            }

            foo(t)
          }
        }
      )
      var isContinue: Boolean = true
      while (isContinue) {
        val body: String = messages.take
        try {
          println("接收到命令，指定Actor处理。")
          println(body)
          val cmd: Command = CommandFactory.buildCommand(config, body, session, sc)
          if ("exit" == cmd.getCommand) {
            isContinue = false
          } else {
            import scala.collection.JavaConversions._
            for (receiver <- receivers) {
              if (receiver.getSupport == cmd.getCommand) {
                val actor = context.actorOf(Props(new Schedule(rabbitMQAgent, body, failureQueue)))
                actor ! (receiver, cmd)
              }
            }
          }
        } catch {
          case e: Exception =>
            val `object`: JsonObject = new JsonObject
            val o: ByteArrayOutputStream = new ByteArrayOutputStream
            val out: PrintStream = new PrintStream(o)
            e.printStackTrace(out)
            out.flush()
            `object`.addProperty("origin", body)
            `object`.addProperty("exception", new String(o.toByteArray))
            Try(o.close)
            Try(out.close)
            rabbitMQAgent.produceText("", failureQueue, Toolkit.JSON.toJson(`object`), true)
        }
      }

      self ! Stop()
    }
  }

}

class Schedule(val rabbitMQAgent: RabbitMQAgent, val body: String, val failureQueue: java.lang.String) extends Actor {
  override def receive: Receive = {
    case (receiver: CommandReceiver, cmd: Command) => {
      val action = context.actorOf(Props(new Action(rabbitMQAgent, body, failureQueue)))
      action ! (receiver, cmd)
    }
    case complete: Boolean => {
      println(if (complete) "完成" else "失败")
      context stop self
    }
  }
}

class Action(val rabbitMQAgent: RabbitMQAgent, val body: String, val failureQueue: java.lang.String) extends Actor {
  override def receive: Receive = {

    case (receiver: CommandReceiver, cmd: Command) => {
      try {
        receiver.execute(cmd)
        sender ! true
        context stop self
      } catch {
        case e: Exception => {
          e.printStackTrace()
          val `object`: JsonObject = new JsonObject
          val o: ByteArrayOutputStream = new ByteArrayOutputStream
          val out: PrintStream = new PrintStream(o)
          e.printStackTrace(out)
          out.flush()
          `object`.addProperty("origin", body)
          `object`.addProperty("exception", new String(o.toByteArray))
          Try(o.close)
          Try(out.close)
          rabbitMQAgent.produceText("", failureQueue, Toolkit.JSON.toJson(`object`), true)
          sender ! false
          context stop self
        }
      }
    }
  }
}