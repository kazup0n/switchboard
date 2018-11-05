package switcboard.logging.actor


import akka.actor.{Actor, ActorLogging}
import switcboard.logging.{JsonFormatSupport, MapLogRecord, StringLogRecord}

class LogActor extends Actor with JsonFormatSupport with ActorLogging {

  override def receive: Receive = {
    case r: StringLogRecord => {
      if (log.isEnabled(r.level)) {
        log.notifyLog(r.level, format(r.msg, r.throwable))
      }
    }
    case r: MapLogRecord => {
      if (log.isEnabled(r.level)) {
        log.notifyLog(r.level, format(r.msg, r.throwable))
      }
    }
  }
}
