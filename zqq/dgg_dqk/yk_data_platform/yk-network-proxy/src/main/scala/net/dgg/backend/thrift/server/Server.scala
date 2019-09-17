package net.dgg.backend.thrift.server

import java.util

import net.dgg.backend.thrift.proxy.ThriftProxyService
import net.dgg.backend.thrift.server.provider.ThriftProxyServiceProvider
import net.dgg.yk.common.Toolkit
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.server.{TServer, TSimpleServer}
import org.apache.thrift.transport.TServerSocket
import org.yaml.snakeyaml.Yaml

class ServerBoot extends Runnable {

  override def run(): Unit = {
    val yaml = new Yaml
    val config: util.Map[String, AnyRef] = yaml.loadAs(classOf[Server].getResourceAsStream("/application.yml"), classOf[util.Map[String, AnyRef]])
    val mongoHost: String = Toolkit.MapHelper.getDeepValue(config, "mongo.host".split("\\.+"))
    val mongoPort: String = Toolkit.MapHelper.getDeepValue(config, "mongo.port".split("\\.+"))
    val mongoUsername: String = Toolkit.MapHelper.getDeepValue(config, "mongo.username".split("\\.+"))
    val mongoPassword: String = Toolkit.MapHelper.getDeepValue(config, "mongo.password".split("\\.+"))

    val serviceProvider = new ThriftProxyServiceProvider(
      mongoHost.split(","),
      mongoPort.split(",").map(Integer.valueOf),
      mongoUsername,
      mongoPassword
    )

    try {
      System.out.println("ThriftProxyService TSimpleServer start ....")
      val tprocessor = new ThriftProxyService.Processor[ThriftProxyService.Iface](serviceProvider)
      val serverTransport = new TServerSocket(12345)
      val tArgs = new TServer.Args(serverTransport)
      tArgs.processor(tprocessor)
      tArgs.protocolFactory(new TBinaryProtocol.Factory)
      val server = new TSimpleServer(tArgs)
      System.out.println("ThriftProxyService is online......")
      server.serve()
    } catch {
      case e: Exception =>
        System.out.println("service start fail")
        throw new IllegalStateException(e)
    }
  }

}
