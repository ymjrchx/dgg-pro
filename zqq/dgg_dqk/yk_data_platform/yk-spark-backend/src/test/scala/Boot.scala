import com.google.gson.JsonObject
import net.dgg.yk.platform.backend.common.MongoDao
import net.dgg.yk.platform.backend.rabbitmq.RabbitMQAgent
import org.apache.curator.utils.CloseableUtils

class Boot {

  def m2h120_one_month() = {
    val client = MongoDao.getNormalMongoClient(Array("10.2.1.218", "10.2.1.216"), Array(57017, 57017).map(Integer.valueOf), "admin", "48bb67d7996f327b")
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    mongo120_one_month.foreach {
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

  def m2h218_one_month() = {
    val client = MongoDao.getNormalMongoClient(Array("10.2.1.218", "10.2.1.216"), Array(57017, 57017).map(Integer.valueOf), "admin", "48bb67d7996f327b")
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    mongo218_one_month.foreach {
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

  def h2t(): Unit = {
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action.local"
    h2t_data.foreach {
      case (database: String, collection: String) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "h2t")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        obj.addProperty("index", "dgg_dqc_es_db_brand4")
        obj.addProperty("type", "companyBrand4")
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
  }

  def h2e_commercial() = {
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    mongo120_one_month.foreach {
      case (database: String, collection: String) => {
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

  def h2e_yearReport() = {
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action"
    mongo120_one_month.foreach {
      case (database: String, collection: String) => {
        val obj: JsonObject = new JsonObject
        obj.addProperty("command", "h2e")
        obj.addProperty("database", database)
        obj.addProperty("collection", collection)
        obj.addProperty("index", "dgg_yk_year_report")
        obj.addProperty("type", "dgg_yk_year_report")
        obj.addProperty("taskConfig", "/mapping/yearReport.json")
        obj.addProperty("idField", "companyId")
        rabbitmq.produceText("", action, obj.toString, true)
      }
    }
  }

  def exit() = {
    val rabbitmq = RabbitMQAgent.builder().host("192.168.254.181").port(5672).vHost("/").user("admin").password("admin").build
    val action = "yk.dataplatform.action.local"
    rabbitmq.produceText("", action, "{\"command\":\"exit\"}", true)
  }

  def h2t_data() = {
    Array(
      ("shangbiao_db2018", "detail_results_new"),
      ("shangbiao_db2017", "detail_results_new"),
      ("shangbiao_db2016", "detail_results_new"),
      ("shangbiao_db2015", "detail_results_new"),
      ("shangbiao_db_2012-2013-2014", "detail_results_new"),
      ("shangbiao_db_2007.to.2011", "detail_results_new"),
      ("shangbiao_db.lt2007yyyyMMdd", "detail_results_new")
    )
  }

  def h2e_dat() = {
    val list = Array(
      ("qixin_com", "detail_results"),

      ("shuidi_com", "12_detail_results"),
      ("shuidi_com", "12detail_results"),
      ("shuidi_com", "1_detail_results"),
      ("shuidi_com", "detail_results"),

      ("qichamao_com", "detail_results_12"),
      ("qichamao_com", "detail_results_ly"),
      ("qichamao_com", "detail_results_1"),
      ("qichamao_com", "detail_results_wgp"),

      ("tyc_com", "detail_results"),

      ("qichacha_com", "temp_one_page_results"),
      ("qichacha_com", "inst_dgg_results"),
      ("qichacha_com", "m1-one_page_results"),
      ("qichacha_com", "m12-one_page_results"),
      ("qichacha_com", "m12-one_page_results"),

      ("zhongyi_com", "detail_results"),

      ("bdxy_com", "detail_results")
    )
    list
  }

  def mongo120_one_month() = {
    val list = Array(
      ("qichacha_com", "m1-one_page_results")
    )
    list
  }

  def mongo218_one_month() = {
    val list = Array(
      ("qixin_com", "m1-one_page_results"),
      ("shuidi_com", "1_detail_results"),
      ("qichamao_com", "detail_results_1"),
      ("qichacha_com", "m1-one_page_results")
    )
    list
  }

}