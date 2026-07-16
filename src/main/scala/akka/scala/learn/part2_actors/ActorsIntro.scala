package akka.scala.learn.part2_actors

import akka.actor.{Actor, ActorSystem, Props}

object ActorsIntro extends App {

  // 1. Create Actor system
  val actorSystem = ActorSystem("firstActorSystem")

  /*
  2. create actors
  - Actors can communicate asynchronously
  - No two actor with same name can be created in the same actor system
  - Actor are fully encapsulated that means
    - no other actor can access the internal state of another actor
    - no other actor can call methods on another actor
    - can not instantiate an actor using new keyword
  - Actor can only communicate via messages
   */
  private class WordCountActor extends Actor {
    private var totalWords: Int = 0
    // Receive = PartialFunction[Any, Unit]
    override def receive: Receive = {
      case message: String =>
        println(s"[word counter]: $message")
        totalWords += message.split(" ").length
      case unknown => println(s"[word counter]: I can not understand ${unknown.toString}")
    }
  }

  // 3. instantiate actors
  private val wordCounter = actorSystem.actorOf(Props[WordCountActor], "wordCounter")

  // 4. communicate
  wordCounter ! "I am learning Akka and it's pretty damn cool!"
  wordCounter ! 10

  // instantiate an actor if class has constructor parameters
  private class Person(name: String) extends Actor {
    override def receive: Receive = {
      case message: String => println(s"[Person]: I received a message: $message")
    }
  }

  private val person = actorSystem.actorOf(Props(new Person("Bob")), "person")
  person ! "Hi, I am Bob"

  object Person {
    def props(name: String): Props = Props(new Person(name))
  }
  // recommended: instantiate an actor if class has constructor parameters
  private val companionPerson = actorSystem.actorOf(Person.props("Alice"), "companionPerson")
  companionPerson ! "Hi, I am Alice"

}
