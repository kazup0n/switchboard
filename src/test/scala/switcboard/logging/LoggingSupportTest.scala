package switcboard.logging

import org.scalatest.FunSuite
import switcboard.logging.actor.LogActorSystem

import scala.concurrent.Await
import scala.concurrent.duration._

class LoggingSupportTest extends FunSuite with LoggingSupport {

  test("logging"){
    implicit val context = DiagnosticContext()

    debug("debug")
    info("info")
    warn("warn")
    error("error", new RuntimeException())

    Await.ready(LogActorSystem.system.shutdown(30 seconds), 30 seconds)
  }

}
