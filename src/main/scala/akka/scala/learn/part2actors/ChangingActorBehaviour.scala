package akka.scala.learn.part2actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ChangingActorBehaviour extends App {

  val actorSystem = ActorSystem("changingActorBehaviour")

  private object FussyKid {
    val SAD = "sad"
    val HAPPY = "happy"
    case object KidAccept
    case object KidReject
  }
  private class FussyKid extends Actor {
    import Mom._
    import FussyKid._

    var state = HAPPY
    def receive: Receive = {
      case Food(VEGETABLE) => state = SAD
      case Food(CHOCOLATE) => state = HAPPY
      case ASK(_) =>
        if(state == HAPPY) sender() ! KidAccept
        else sender() ! KidReject
    }
  }

  private class StateLessFussyKid extends Actor {
    import Mom._
    import FussyKid._

    def receive: Receive = happyReceive

    private def happyReceive: Receive = {
      /*
       For next message sadReceive will be treated as receive method
       explain: context.become(newHandler, true/false)
        - true: fully replace the old message handler with a new message handler
        - false: Instead of replacing old message handler, It will simply add new entry in to
          message handler stack.
        - when an actor needs to handle a message, it will pick the top handler from stack.
        - If message handler stack is empty, then it will call the plain receive method.
       context.unbecome() will be used to remove the handler from stack.
       */
      case Food(VEGETABLE) => context.become(sadReceive)
      case ASK(_) => sender() ! KidAccept
    }
    private def sadReceive: Receive = {
      case ASK(_) => sender() ! KidReject
    }
  }

  object Mom {
    val VEGETABLE = "vegetable"
    val CHOCOLATE = "chocolate"
    case class MomStart(kidRef: ActorRef)
    case class Food(food: String)
    case class ASK(message: String)
  }
  private class Mom extends Actor {
    import Mom._
    import FussyKid._

    override def receive: Receive = {
      case MomStart(kidRef) =>
        kidRef ! Food(CHOCOLATE)
        kidRef ! ASK("want to play?")
      case KidReject => println("My kid is sad, but he is healthy")
      case KidAccept => println("Yay, my kid is happy")
    }
  }

  private val mom = actorSystem.actorOf(Props[Mom], "mom")
  private val fussyKid = actorSystem.actorOf(Props[FussyKid], "fussyKid")
  private val stateLessFussyKid = actorSystem.actorOf(Props[StateLessFussyKid], "stateLessFussyKid")
  import Mom._
  mom ! MomStart(fussyKid)
  mom ! MomStart(stateLessFussyKid)
}
