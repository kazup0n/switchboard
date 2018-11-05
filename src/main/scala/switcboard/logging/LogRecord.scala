package switcboard.logging

import java.util.UUID

import akka.event.Logging.LogLevel

sealed trait AnyId

case class RequestId(trackingId: String = UUID.randomUUID().toString) extends AnyId

case object EmptyId extends AnyId


case class DiagnosticContext(reqId: AnyId = EmptyId)

class StringLogRecord(_msg: => String,
                              val level:LogLevel,
                              val context:DiagnosticContext,
                              val throwable: Throwable)
{

  lazy val msg:String = _msg

}

class MapLogRecord(_msg: => Map[String, String],
                   val level:LogLevel,
                   val context:DiagnosticContext,
                    val throwable: Throwable)
{

  lazy val msg:Map[String, String] = _msg
}