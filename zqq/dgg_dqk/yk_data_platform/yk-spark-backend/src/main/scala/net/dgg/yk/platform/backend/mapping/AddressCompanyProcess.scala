package net.dgg.yk.platform.backend.mapping

import com.google.gson.JsonObject
import net.dgg.yk.platform.backend.mapping.ints.Process
import net.dgg.yk.platform.backend.meta.jsonUtils._
import scala.collection.JavaConverters._

class AddressCompanyProcess extends Process {

  override def process(task: JsonObject, context: JsonObject): AnyRef = {
    val json = context.get("docs.background.reportInfo")
    var returnObj: String = null
    if (json != null) {
      if (json.isJsonObject) {
        val set = json.getAsJsonObject.entrySet().asScala
        if (set.size > 0) {
          val ent = set.iterator.next
          val `val` = ent.getValue
          returnObj = `val`.getStringByPath("rBaseInfo.rAddress")
        }
      }
    }
    returnObj
  }

}
