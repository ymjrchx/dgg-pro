import com.google.gson.JsonObject
import net.dgg.yk.common.Toolkit
import net.dgg.yk.platform.backend.common.MongoDao
import net.dgg.yk.platform.backend.rabbitmq.RabbitMQAgent
import org.apache.curator.utils.CloseableUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.bson
import org.bson.Document

class Test1 {

  def m2hlocal(): Unit = {
    val client = MongoDao.getNormalMongoClient(Array("10.2.1.218", "10.2.1.216"), Array(57017, 57017).map(Integer.valueOf), "admin", "48bb67d7996f327b")
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "_yk.dataplatform.action"
    Array(("qichamao_com", "detail_results_ly"), ("shuidi_com", "12detail_results")).foreach {
      case (database, collection) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "m2h")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
    CloseableUtils.closeQuietly(client)
  }

  def all2esCommercialLocal() = {
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "_yk.dataplatform.action"
    commercial_in().foreach {
      case (database, collection) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "h2e")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        obj.addProperty("index", "dgg_yk_commercial")
        obj.addProperty("type", "dgg_yk_parent_clues")
        obj.addProperty("taskConfig", "/mapping/commercialConf.json")
        obj.addProperty("idField", "companyId")
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
  }

  //企信到HDFS本地
  def qixin_com2hdfsLcal(): Unit = {
    val client = MongoDao.getNormalMongoClient(Array("10.2.1.218", "10.2.1.216"), Array(57017, 57017).map(Integer.valueOf), "admin", "48bb67d7996f327b")
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "_yk.dataplatform.action"
    commercial_in().foreach {
      case (database, collection) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "m2h")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)

        obj.addProperty("refHosts", "10.2.1.218,10.2.1.216")
        obj.addProperty("refPorts", "57017,57017")
        obj.addProperty("refUsername", "admin")
        obj.addProperty("refPwd", "48bb67d7996f327b")

        obj.addProperty("refDatabase", "all_com")
        obj.addProperty("refCollection", "all_results")

        val refProjection = new JsonObject
        refProjection.addProperty("docs.background.baseInfo.companyTel", 1)
        obj.add("refProjection", refProjection);

        obj.addProperty("refField", "refs")
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
    CloseableUtils.closeQuietly(client)
  }

  def mergeAnd2esCommercialLocal() = {
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "_yk.dataplatform.action"
    commercial_in().foreach {
      case (database: String, collection: String) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "mergeTelephoneImport")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        obj.addProperty("database1", "n")
        obj.addProperty("collection1", "u")
        obj.addProperty("index", "dgg_yk_commercial")
        obj.addProperty("type", "dgg_yk_parent_clues")
        obj.addProperty("taskConfig", "/mapping/commercialConfWithMerge.json")
        obj.addProperty("idField", "companyId")
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
  }

  def mergeAnd2esCommercialServer() = {
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    commercial().foreach {
      case (database, collection) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "mergeTelephoneImport")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        obj.addProperty("database1", "all_com")
        obj.addProperty("collection1", "all_results")

        //StorageLevel.DISK_ONLY
        //StorageLevel.MEMORY_AND_DISK
        //StorageLevel.DISK_ONLY_2
        //StorageLevel.MEMORY_AND_DISK_2
        //StorageLevel.MEMORY_AND_DISK_SER
        //StorageLevel.MEMORY_AND_DISK_SER_2
        //StorageLevel.MEMORY_ONLY
        //StorageLevel.MEMORY_ONLY_2
        //StorageLevel.MEMORY_ONLY_SER
        //StorageLevel.MEMORY_ONLY_SER_2

        obj.addProperty("targetStorageLevel", "")
        obj.addProperty("primaryStorageLevel", "MEMORY_AND_DISK")

        obj.addProperty("index", "dgg_yk_commercial")
        obj.addProperty("type", "dgg_yk_parent_clues")
        obj.addProperty("taskConfig", "/mapping/commercialConfWithMerge.json")
        obj.addProperty("idField", "companyId")
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
  }

  def commercial_in() = {
    val list = Array(
      ("qixin_com", "detail_results")
    )
    list
  }


  def showStatus(path: String) = {
    val configuration = new Configuration
    configuration.set("fs.defaultFS", "hdfs://master:8020")
    val fs = FileSystem.get(configuration)
    val it = fs.listStatus(new Path(path))
    for (i <- 0 until it.length) {
      println(it(i))
    }
  }


  //所有的HDFS数据库COUNT输出到控制台
  def allOfCount(): Unit = {
    var rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    commercial().foreach {
      case (database, collection) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "count")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
  }

  def mongoCounts(): Unit = {
    val hosts = Array[String]("10.2.1.218", "10.2.1.216");
    val ports = Array[Int](57017, 57017).map(Integer.valueOf)
    val client = MongoDao.getNormalMongoClient(hosts, ports, "admin", "48bb67d7996f327b")
    commercial().foreach {
      case (database, collection) => {
        val c = client.getDatabase(database).getCollection(collection)
        val count = c.count()
        println(s"database: ${database}\t\tcollection: ${collection}\t\tcount: ${count}")
      }
    }
    CloseableUtils.closeQuietly(client)
  }

  def listCollections(): Unit = {
    val client = MongoDao.getNormalMongoClient(Array[String]("10.2.1.218", "10.2.1.216"), Array[Int](57017, 57017).map(Integer.valueOf), "admin", "48bb67d7996f327b")
    val iterator = client.listDatabaseNames().iterator()
    while (iterator.hasNext) {
      val db = iterator.next
      val collectionCursor = client.getDatabase(db).listCollectionNames().iterator()
      while (collectionCursor.hasNext) {
        val collection = collectionCursor.next
        println(s"database: ${db}\tcollection: ${collection}")
      }
    }
    client.close
  }

  def allCom2hdfs(): Unit = {
    val dbCs = Array(
      ("all_com", "1_all_results"),
      ("all_com", "10_all_results"),
      ("all_com", "11_all_results"),
      ("all_com", "12_all_results"),
      ("all_com", "2_all_results"),
      ("all_com", "3_all_results"),
      ("all_com", "4_all_results"),
      ("all_com", "5_all_results"),
      ("all_com", "6_all_results"),
      ("all_com", "7_all_results"),
      ("all_com", "8_all_results"),
      ("all_com", "9_all_results"),
      ("all_com", "all_results")
    )
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    dbCs.foreach {
      case (database, collection) => {
        val command: JsonObject = new JsonObject
        command.addProperty("command", "m2h")
        command.addProperty("database", database)
        command.addProperty("collection", collection)
        command.addProperty("size", "3000")
        rabbitmq.produceText("", action, Toolkit.JSON.toJson(command), true)
      }
    }
  }

  def sparkTest(): Unit = {
    val conf = new SparkConf
    conf.set("spark.master", "local[16]")
    conf.set("spark.app.name", "hdfs")
    val sc = new SparkContext(conf)
    val qixin = sc.objectFile[Document]("hdfs://master:8020/data/v2/sink/zhongyi_com/detail_results/origin")
    println(qixin.count)
  }


  def testId(): Unit = {
    val client = MongoDao.getNormalMongoClient(Array[String]("10.2.1.218", "10.2.1.216"), Array[Int](57017, 57017).map(Integer.valueOf), "admin", "48bb67d7996f327b")
    val id: String = "117942331fc86bceb984e43e7799673c"
    commercial().foreach {
      case (db, c) => {
        val database = client.getDatabase(db)
        val collection = database.getCollection(c)
        val mongoCursor = collection.find(new bson.Document("_id", id)).iterator()
        if (mongoCursor.hasNext) {
          println(s"database\t${db},collection:\t${c}")
        }
      }
    }
  }

  //所有要跑的企业数据，数据源
  def commercial() = {
    val list = Array(
      ("qixin_com", "detail_results"),
      ("shuidi_com", "12_detail_results"),
      ("shuidi_com", "12detail_results"),
      ("shuidi_com", "1_detail_results"),
      ("qichamao_com", "detail_results_12"),
      ("qichamao_com", "detail_results_ly"),
      ("qichamao_com", "detail_results_1"),
      ("tyc_com", "detail_results"),
      ("qichacha_com", "temp_one_page_results"),
      ("zhongyi_com", "detail_results"),
      ("shuidi_com", "detail_results"),
      ("qichamao_com", "detail_results_wgp"),
      ("bdxy_com", "detail_results")
    )
    list
  }

  def commercial12() = {
    val list = Array(
      ("qichacha_com", "temp_one_page_results"),
      ("qichacha_com", "m12-one_page_results"),
      ("qichacha_com", "m1-one_page_results")
    )
    list
  }

  def allMongo2company() = {
    var rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    commercial().foreach {
      case (database, collection) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "companyImport")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        obj.addProperty("index", "dgg_yk_company")
        obj.addProperty("type", "dgg_yk_company")
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
  }

}
