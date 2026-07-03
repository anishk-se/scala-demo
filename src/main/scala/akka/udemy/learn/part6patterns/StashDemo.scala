package akka.udemy.learn.part6patterns

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Stash}

object StashDemo extends App {

  val actorSystem = ActorSystem("StashDemo")

  private case object Open
  private case object Close
  private case object Read
  private case class Write(data: String)

  private class ResourceActor extends Actor with ActorLogging with Stash {
    private var innerDB: String = ""

    def receive: Receive = closed

    private def closed: Receive = {
      case Open =>
        log.info("[closed]: Opening Resource")
        unstashAll()
        context.become(open)
      case message =>
        log.info(s"[closed]: $message,I am closed")
        stash()
    }

    private def open: Receive = {
      case Read => log.info(s"[open]: I have read $innerDB")
      case Write(data) =>
        log.info(s"[open]: I am writing $data")
        innerDB = data
      case Close =>
        log.info("[open]: closing resources")
        unstashAll()
        context.become(closed)
      case message =>
        log.info(s"[open]: $message, I am in open state")
        stash()
    }

  }

  private val resourceActor = actorSystem.actorOf(Props[ResourceActor], "resourceActor")


  resourceActor ! Read
  resourceActor ! Open
  resourceActor ! Open
  resourceActor ! Write("I love stash")
  resourceActor ! Close
  resourceActor ! Read

}
