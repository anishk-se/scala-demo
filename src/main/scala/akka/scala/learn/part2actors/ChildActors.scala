package akka.scala.learn.part2actors

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}

/*
Guardian Actor: Every AKKA actor system has three guardian actor
  1. /system: it is a system guardian, user for logging and all
  2. /user: every actor we as a programmer creates are owned by /user.
  3. /: the root guardian, manages both system and user guardian.
 */
object ChildActors extends App {

  val actorSystem = ActorSystem("childActors")

  object ParentActor {
    case class CreateChild(name: String)
    case class TellChild(message: String)
  }
  class ParentActor extends Actor {
    override def receive: Receive = {
      case ParentActor.CreateChild(name) =>
        val childRef = context.actorOf(Props[ChildActor], name)
        context.become(withChild(childRef))
    }

    def withChild(childRef: ActorRef): Receive = {
      case ParentActor.TellChild(message) => childRef forward message
    }
  }

  class ChildActor extends Actor {
    override def receive: Receive = {
      case message => println(s"[${self.path}] I got message: $message")
    }
  }

  val parentActor = actorSystem.actorOf(Props[ParentActor], "parentActor")
  parentActor ! ParentActor.CreateChild("child")
  parentActor ! ParentActor.TellChild("Hey kid")

  // find an actor by their path, ActorSelection is a child of ActorRef
  val childSelection: ActorSelection = actorSystem.actorSelection("/user/parentActor/child")
  childSelection ! "I found you"

  // NEVER PASS MUTABLE STATE OF ACTOR, OR `this` TO THE CHILD ACTORS.
}
