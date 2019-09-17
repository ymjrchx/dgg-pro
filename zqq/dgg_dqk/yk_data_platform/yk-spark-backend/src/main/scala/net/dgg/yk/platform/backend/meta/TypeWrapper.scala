package net.dgg.yk.platform.backend.meta

import com.google.gson.{JsonElement, JsonObject}
import net.dgg.yk.common.Toolkit._
import org.apache.commons.lang3.StringUtils

class StringWrapper(val src: String) {

  def toArrayByDot(): Array[String] = {
    src.split("\\.+")
  }

  def toJsonObject(): JsonObject = {
    JSON.parseJsonObject(src)
  }

  def splitBy(split: String): Array[String] = {
    src.split(split)
  }

}

class JsonWrapper(val json: JsonElement) {

  import stringUtils._

  def getStringByPath(path: String): String = {
    if (StringUtils.isNotEmpty(path)) {
      if (json != null && json.isJsonObject) {
        val paths = path.toArrayByDot
        JSON.getDeepValueOrNull(json.getAsJsonObject, paths, classOf[String])
      } else {
        null
      }
    } else {
      throw new IllegalStateException(s"参数 path = ${path} ,不能是空")
    }
  }

  def get(path: String): JsonElement = {
    if (json != null && json.isJsonObject) {
      val paths = path.toArrayByDot
      JSON.getDeepValueOrNull(json.getAsJsonObject, paths, classOf[AnyRef]).asInstanceOf[JsonElement]
    } else {
      return null
    }
  }

}

object stringUtils {

  implicit def toStringWrapper(src: String): StringWrapper = new StringWrapper(src)

}

object jsonUtils {

  implicit def toJsonWrapper(src: JsonElement): JsonWrapper = new JsonWrapper(src)

}

