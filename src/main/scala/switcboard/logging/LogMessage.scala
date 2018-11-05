package switcboard.logging

class LogMessage[T]

object LogMessage {

  implicit object StringLogMessage extends LogMessage[String]

  implicit object MapLogMessage extends LogMessage[Map[String, String]]

}





