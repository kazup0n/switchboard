package switcboard.logging

import akka.event.Logging.LogLevel
import akka.stream.Attributes.LogLevels
import switcboard.logging.actor.LogActorSystem

import scala.reflect.ClassTag

trait LoggingSupport {

  private[logging] def notify[T: LogMessage](msg: => T, throwable: Throwable)(level: LogLevel)(implicit context:DiagnosticContext, c:ClassTag[T]) = {
    msg match {
      case s:String => LogActorSystem.system.actor ! new StringLogRecord(s, level, context, throwable)
      case m:Map[String, String]  => LogActorSystem.system.actor ! new MapLogRecord(m, level, context, throwable)
    }

  }

  def debug[T: LogMessage](msg: => T)(implicit context: DiagnosticContext, c:ClassTag[T]) = notify(msg, null)(LogLevels.Debug)

  def info[T: LogMessage](msg: => T)(implicit context: DiagnosticContext, c:ClassTag[T]) = notify(msg, null)(LogLevels.Info)

  def warn[T: LogMessage](msg: => T)(implicit context: DiagnosticContext, c:ClassTag[T]) = notify(msg, null)(LogLevels.Warning)

  def error[T: LogMessage](msg: => T, t: Throwable = null)(implicit context: DiagnosticContext, c:ClassTag[T]) = notify(msg, t)(LogLevels.Error)


}
