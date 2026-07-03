package akka.udemy.learn.part1recap

import scala.annotation.tailrec
import scala.util.Try

object GeneralRecap extends App {

  private val aCondition: Boolean = true

  // type can be auto figured out using expression of the right hand side of equal.
  private var aVariable = 42
  aVariable += 1

  // this is an if expression which will return value
  val aConditionedVal = if(aCondition) 42 else 65

  // result of code block is result of its last expression
  val aCodeBlock = { if(aCondition) 74; 54 }

  /*
  Unit: unit denotes the type of expression that only has side effects
  They do something but not return anything meaningful.
  Example:
     1. printing something to the console
     2. Showing something on screen
     3. Changing a variable
     4. Adding something to mutable data structure
   */
  val aUnit: Unit = println("Hello, Scala")

  def aFunction(x: Int): Int = x + 1

  /*
  Tail recursion ia an optimization from scala compiler in that it rewrites
  recursive calls in such a way, so that it does not throw stack over flow error
  due to excessive recursion.
   */
  @tailrec
  private def aFactorial(n: Int, acc: Int): Int = {
    if(n <= 0) acc
    else aFactorial(n-1, acc * n)
  }

  private class Animal
  private class Dog extends Animal
  // Child type object can be assigned to parent type variable (Dog is an Animal)
  private val aDog: Animal = new Dog

  private trait Carnivore {
    def eat(a: Animal): Unit
  }

  // If we extend class and trait both, so it is called mixin
  private class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("Crunch")
  }

  private val aCroc = new Crocodile
  aCroc.eat(aDog)
  // Infix method notation
  aCroc eat aDog

  /*
  Anonymous class is a way to instantiate classes that extends from abstract types
  on the spot without declaring special class for them.
   */
  private val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar")
  }
  aCarnivore eat aDog

  //generics
  abstract class MyList[+A]
  // companion object
  object MyList

  private case class Person(name: String, age: Int)

  val aPotentialFailure = try {
    /*
    Throwing exception returns a special type called Nothing, that means
    it does not return any value, not even null, Unit, not even anything
     */
    throw new RuntimeException("I am innocent, I swear")
  } catch {
    case e: Exception => "I caught an exception"
  } finally {
    println("some logs")
  }

  // Function is an object of class Function, Function1.....Function23
  private val incrementer = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }
  /*
  When scala compiler sees object is called as a function, under the hood it will call
  its apply method. incrementer.apply(42)
   */
  val incremented = incrementer(42)

  // lambda function (trick to write, write like you are writing apply method)
  // Int => Int ==== Function1[Int, Int]
  val anonymousIncrementer = (v1: Int) => v1 + 1

  /*
  Functional Programing is all about working with function as first class.
  that means functions should be used like val and var.
   */

  // HOF: it takes function as an argument or returns function as result.
  List(1, 2, 3).map(incrementer)

  // for comprehension
  val pairs = for {
    num <- List(1, 2, 3)
    char <- List('a', 'b', 'c')
  } yield num + "-" + char
  val pairsLong = List(1, 2, 3).flatMap(num => List('a', 'b', 'c').map(char => num + "-" + char))

  val anOption = Some(2)
  val aTry = Try(throw new RuntimeException)

  private val unknown = 2
  val order = unknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }

  private val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, My name is $n"
    case _ => "I do not know my name"
  }

}
