package akka.udemy.learn.playground

import akka.actor.ActorSystem

object Playground extends App {
  private val actorSystem = ActorSystem("HelloAkka")
  println(actorSystem.name)
}
