package scala.udemy.learn.typeclass

object Convenience extends App {

  trait Equal[T] {
    def apply(value1: T, value2: T): Boolean
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = {
      equalizer.apply(a, b)
    }
  }

  case class Person(name: String, age: Int, email: String)

  implicit object EmailEquality extends Equal[Person] {
    override def apply(person1: Person, person2: Person): Boolean = {
      if(person1.email.matches(person2.email)) true
      else false
    }
  }

  val bob = Person("Bob", 30, "bob@gmail.com")
  val john = Person("John", 32, "john@gmail.com")
  // Compiler looks at arguments bob  : Person and john : Person ---> Infers T = Person
  println(Equal(bob, john)) // Equal.apply[T](bob, john) -> Equal.apply[Person](bob, john)
}
