package switcboard.logging

import play.api.libs.json.{JsObject, Json}

import scala.reflect.ClassTag


private[logging] trait JsonFormatSupport {


  private[logging] def format[T: LogMessage](msg: T, t: Throwable)(implicit c: ClassTag[T]): String = {
    val formatted: Map[String, String] = msg match {
      case s: String => Map("msg" -> s)
      case m: Map[_, _] => m.asInstanceOf[Map[String, String]]
    }
    val msgJson = Json.toJsObject(formatted)
    if (t != null) {
      Json.stringify(msgJson ++ exceptionToJson(t))
    } else {
      Json.stringify(msgJson)
    }
  }

  private def exceptionToJson(t: Throwable): JsObject = {

    def exToJson(t: Throwable): JsObject = {
      if (t != null) {
        Json.obj("ex" -> t.getClass.getName, "msg" -> t.getMessage)
      } else {
        JsObject.empty
      }
    }

    def getStack(ex: Throwable): Seq[JsObject] = {
      val stack = ex.getStackTrace map {
        case trace =>
          val j0 = if (trace.getLineNumber > 0) {
            Json.obj("line" -> trace.getLineNumber)
          } else {
            JsObject.empty
          }
          val j1 = Json.obj(
            "class" -> trace.getClassName,
            "file" -> trace.getFileName,
            "method" -> trace.getMethodName
          )
          j0 ++ j1
      }
      stack
    }

    val stack = getStack(t)
    val j1 = if (t.getCause != null) {
      exceptionToJson(t.getCause)
    } else {
      JsObject.empty
    }
    Json.obj("trace" -> Json.obj("msg" -> exToJson(t), "stack" -> stack)) ++ j1
  }

}
