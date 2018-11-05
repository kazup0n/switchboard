package switcboard.logging.actor

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.gracefulStop

import scala.concurrent.duration.{FiniteDuration}

case class LogActorSystem(system:ActorSystem) {

  val actor:ActorRef = system.actorOf(Props[LogActor])

  def shutdown(timeout:FiniteDuration) = gracefulStop(actor, timeout)

}


object LogActorSystem {
  val system = LogActorSystem(ActorSystem("LogActorSystem"))
}
