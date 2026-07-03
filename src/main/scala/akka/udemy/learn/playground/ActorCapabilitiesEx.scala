package akka.udemy.learn.playground

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCapabilitiesEx extends App {

  val actorSystem = ActorSystem("ActorCapabilitiesEx")

  /*
  1. Create Counter Actor
     - Increment
     - Decrement
     - Print
   */
  private object CounterActor {
    case class Increment(value: Int)
    case class Decrement(value: Int)
    case object Print
  }

  private class CounterActor extends Actor {
    private var counter: Int = 0
    import CounterActor._
    def receive: Receive = {
      case Increment(value) => counter += value
      case Decrement(value) => counter -= value
      case Print => println(s"[CounterActor]: counter = $counter")
    }
  }

  import CounterActor._
  private val counterActor = actorSystem.actorOf(Props[CounterActor])
  counterActor ! Increment(10)
  counterActor ! Decrement(5)
  counterActor ! Print // [CounterActor]: counter = 5

  /*
  2. Create Bank Account actor
     - Receives
       - Deposit an amount
       - Withdraw an amount
       - Print Statement
     - Replies with
       - Success
       - Failure
     - Create Customer Actor to interact with Bank Account Actor
   */
  private object BankAccountActor {
    case class Deposit(amount: Int)
    case class Withdraw(amount: Int)

    case class Success(info: String)
    case class Failure(info: String)
    case class Statement(info: String)
  }
  private class BankAccountActor extends Actor {
    private var balance: Int = 0

    import BankAccountActor._
    def receive: Receive = {
      case Deposit(amount) =>
        if(amount <=0 ) sender() ! Failure(s"Invalid deposit amount = $amount")
        else {
          balance += amount
          sender() ! Success(s"Successfully deposited amount = $amount")
        }
      case Withdraw(amount) =>
        if(amount > balance ) sender() ! Failure(s"Invalid withdrew amount = $amount")
        else {
          balance -= amount
          sender() ! Success(s"Successfully withdrew amount = $amount")
        }
      case Statement => sender() ! Statement(s"Your balance is: $balance")
    }
  }
  private object CustomerActor {
    case class LiveTheLife(bankAcc: ActorRef)
  }
  private class CustomerActor extends Actor {
    import CustomerActor._
    import BankAccountActor._
    def receive: Receive = {
      case LiveTheLife(bankAcc) =>
        bankAcc ! Deposit(1000)
        bankAcc ! Withdraw(5000)
        bankAcc ! Withdraw(500)
        bankAcc ! Statement
      case Success(info) => println(info)
      case Failure(info) => println(info)
      case Statement(info) => println(info)
    }
  }

  private val bankActor = actorSystem.actorOf(Props[BankAccountActor], "bankActor")
  private val customerActor = actorSystem.actorOf(Props[CustomerActor], "customerActor")

  import CustomerActor._
  customerActor ! LiveTheLife(bankActor)

}
