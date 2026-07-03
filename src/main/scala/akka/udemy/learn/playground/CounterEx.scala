package akka.udemy.learn.playground

import akka.actor.{Actor, ActorSystem, Props}

object CounterEx extends App {
  val actorSystem = ActorSystem("counterEx")

  private object StatelessCounterActor {
    case class Increment(inc: Int)
    case class Decrement(dec: Int)
    case object Print
  }
  private class StatelessCounterActor extends Actor {
    import StatelessCounterActor._
    def receive: Receive = countReceive(0)
    private def countReceive(currentCount: Int): Receive = {
      case Increment(inc) => context.become(countReceive(currentCount + inc))
      case Decrement(dec) => context.become(countReceive(currentCount - dec))
      case Print => println(s"[Counter] current count is $currentCount")
    }
  }

  import StatelessCounterActor._
  val counterActor = actorSystem.actorOf(Props[StatelessCounterActor], "statelessCounterActor")
  counterActor ! Increment(10)
  counterActor ! Decrement(5)
  counterActor ! Print
}
