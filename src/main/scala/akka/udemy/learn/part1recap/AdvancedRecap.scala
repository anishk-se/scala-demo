package akka.udemy.learn.part1recap

import scala.concurrent.Future
import scala.language.implicitConversions

object AdvancedRecap extends App {

  /*
  Partial functions are functions that operates only on a subset of given domain.
  If anything else it will throw exception.
  The { case ... } literal is a partial-function literal, if default case is not their
   */
  private val partialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }

  println(partialFunction(2))
  // println(partialFunction(0)) scala.MatchError

  /*
  PartialFunction extends Function1, so it also behaves like a function
  (and will throw MatchError on undefined inputs).
   */
  val aFunction: Int => Int = partialFunction

  val modifiedList = List(1, 2, 3).map{
    case 1 => 42
    case 2 => 65
    case 3 => 999
  }

  // lifted version of partial function, it will be Int => Option[Int]
  private val lifted = partialFunction.lift
  println(lifted(2)) // Some(65)
  println(lifted(5000)) // None

  // adding some more value in partial function domain
  private val pfChain = partialFunction.orElse[Int, Int] {
    case 60 => 9000
  }
  println(pfChain(5)) // 999
  println(pfChain(60)) // 9000

  // type aliases: we can create simpler name for complex type
  private type ReceiveFunction = PartialFunction[Any, Unit]
  def receive: ReceiveFunction = {
    case 1 => println("hello")
    case _ => println("confused...")
  }

  // implicits
  implicit val timeout: Int = 3000
  private def setTimeout(f: () => Unit)(implicit time: Int): Unit = f()
  // extra parameter list is filled by compiler with implicit value in scope
  setTimeout(() => println("timeout"))

  // implicit def
  case class Person(name: String) {
    def greet: String = s"Hi, my name is $name"
  }
  implicit def fromStringToPerson(str: String): Person = Person(str)
  /*
  compiler will convert "Peter" to Person("Peter") and then call greet method
  First compiler will look in String class for greet method,
  if it does not find it, it will look which class has greet method
  and then it will look for implicit conversion from String to that class,
  if it finds it, it will convert and call the method
   */
  println("Peter".greet)

  // implicit classes
  implicit class Dog(name: String) {
    def bark: String = s"$name says Woof!"
  }
  /*
  compiler will convert "Buddy" to Dog("Buddy") and then call bark method
  First compiler will look in String class for bark method,
  if it does not find it, it will look which class has bark method
  and then it will look for implicit class which can take String as constructor parameter,
  if it finds it, it will convert and call the method
   */
  println("Buddy".bark)

  /*
  implicits in local scope:
  sorted method takes an implicit Ordering[T] parameter,
  so compiler will look for implicit value of type Ordering[Int] in scope
   */
  implicit val descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  private val result = List(1, 2, 3).sorted
  println(result) // List(3, 2, 1)

  /*
  imported scope
  Future apply method requires an ExecutionContext in scope,
  so we can import it, predefined implicit ExecutionContext
   */
  import scala.concurrent.ExecutionContext.Implicits.global
  val future = Future {
    println("Hello, Future!")
  }


  // companion object scope
  object Person {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan {
      (a, b) => a.name.compareTo(b.name) < 0
    }
  }
  private val persons = List(Person("Bob"), Person("Alice"))
  println(persons.sorted) // List(Person(Alice), Person(Bob))

  // order that the compiler uses to fetch the implicit value: local -> imported -> companion
}
