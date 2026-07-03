package akka.udemy.learn.part2actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCapabilities extends App {

  val actorSystem = ActorSystem("actorCapabilities")

  private class SimpleActor extends Actor {
    override def receive: Receive = {
      case message: String => println(s"[SimpleActor]: I have received: $message")
      case number: Int => println(s"[SimpleActor]: I have received NUMBER: $number")
      case SpecialMessage(content) => println(s"[SimpleActor]: I have received SpecialMessage: $content")
    }
  }

  private val aSimpleActor = actorSystem.actorOf(Props[SimpleActor], "simpleActor")
  aSimpleActor ! "hello actor"

  /*
  1. message can be of any type, but type must be handled in receive method, and
    - must be IMMUTABLE
    - must be SERIALIZABLE (so we often use, case classes and object)
   */
  aSimpleActor ! 42
  private case class SpecialMessage(content: String)
  aSimpleActor ! SpecialMessage("some special content")

  /*
  2. Actors have information about their context, and about themselves
  - Each actor has a member called `context`, it is a complex data structure that has reference to
    information regarding the environment this actor run in.
  - context.system: it has access to the actor system, this actor runs on top of.
  - context.self: this context has access to this actor's own reference.
    - context.self ==== self ==== this in OOP
   */

  private class CallYourSelf extends Actor {
    def receive: Receive = {
      case message: String => println(s"[$self]: $message")
      case SendMessageToYourSelf(message) => self ! message
    }
  }

  private case class SendMessageToYourSelf(message: String)
  private val callYourSelfActor = actorSystem.actorOf(Props[CallYourSelf], "callYourSelf")
  callYourSelfActor ! SendMessageToYourSelf("I am an actor, and I am proud of it.")

  /*
  3. Communication between two actor
     - Alice: send Hi to Bob
     - Bob: reply How are you to Alice
  - context.sender(): For every actor at any moment in time context.sender() ==== sender()
    contains the actor reference of the actor who last send the message.
  - whenever an actor sends a message to another actor they pass themselves as the sender.
  - !(message: Any)(implicit sender: ActorRef = Actor.noSender)
  - bob.!("Hi") -> if we do not pass sender explicitly, then implicit will apply i.e. context.self
   */
  private class BobActor extends Actor {
    def receive: Receive = {
      case message: String =>
        println(s"[$self]: $message")
        sender() ! "How are you!"
      case WirelessPhoneMessage(content, ref) => ref forward s"Hey ${ref.path.name}, $content"
    }
  }

  private class AliceActor extends Actor {
    def receive: Receive = {
      case message: String => println(s"[$self]: $message")
    }
  }

  private val alice = actorSystem.actorOf(Props[AliceActor], "aliceActor")
  private val bob = actorSystem.actorOf(Props[BobActor], "bobActor")

  // [Actor[akka://actorCapabilities/user/bobActor#-874402283]]: Hi
  // [Actor[akka://actorCapabilities/user/aliceActor#1404412648]]: How are you!
  bob.!("Hi")(alice)

  /*
  4. deadletters
  - if no sender is available not even implicit self and not even explicitly given the default
    value of sender is Actor.noSender ==== null
  - deadletters is a fake Actor inside AKKA which take care to receive message where sender
    is Actor.noSender.
   */
  bob ! "Hi"

  /*
  5. Forwarding messages
  - receiverActor forward message -> this will send message to receiver actor.
  - A -> B forward C, So for C sender will be still A
   */
  private case class WirelessPhoneMessage(content: String, ref: ActorRef)
  bob ! WirelessPhoneMessage("Dead letter is calling you", alice)
}
