package core.scala.learn.part3_patterns

import scala.util.Random

// Pattern matching will try to match a value against multiple part3_patterns
object BasicPattern extends App {

  val random = Random
  val x = random.nextInt(10)

  // 1. switch on constants
  val description = x match {
    case 1 => "The One"
    case 2 => "Double or Nothing!"
    case 3 => "Third time is the charm"
    case _ => "Something Else" // _ is a wild card, and it will match anything
  }
  println(x)
  println(description)

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 22)

  // 2. Case classes: case classes has ability to be deconstructed or extracted in pattern matching.
  val greetings = bob match {
    case Person(n, a) if a > 21 => s"Hi, my name is $n and I can drink"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I do not know who I am?"
  }
  println(greetings)

  // 3. What if no pattern match? You will get MatchError, so make sure to add default case.

}
