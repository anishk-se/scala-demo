package scala.udemy.learn.implicits

import scala.language.implicitConversions

object Introduction extends App {

  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  /*
  1. Implicit Method Call
  - greet method does not exist for String class
  - Compiler looks for anything, that can turn this String into something that has a greet method.
  - It will look for all implicit classes, objects, values and methods, that can help.
   */
  implicit def fromStringToPerson(str: String): Person = Person(str)
  println("Anish".greet) // println(fromStringToPerson("Anish").greet

  case class Parrot(name: String) {
    def greet = s"Hi, my name is $name"
  }
  /*
  - Compilation error:
    - type mismatch;
    - found   : String("Anish")
    - required: ?{def greet: ?}
  - Note that implicit conversions are not applicable because they are ambiguous.
   */
  // implicit def fromStringToParrot(str: String): Parrot = Parrot(str)

  /*
  2. Implicit Method Parameter
   */
  def increment(number: Int)(implicit inc: Int): Int = number + inc

  implicit val defaultInc: Int = 1
  // (implicit inc: Int): let the compiler fetch the implicit value and pass the parameter.
  val resultDefaultInc = increment(10) // 11
  // We can alos use the second parmeter list, if we want
  val resultInc = increment(10)(10) // 20

}
