package scala.udemy.learn.objects

object Companions extends App {

  object Config {
    val version = "1.0"
    def describe(): String = s"Version $version"
  }

  /*
  Writing object Config does two things at once:
   - Defines a class (internally named Config$)
   - Creates the one and only instance of the class, Config$.
   - Scala guarantees exactly one instance of it exists, created on first use, and every reference to
     "Config" in your code is really a reference to that one instance.
   - so when you're explicitly trying to write (class Config$), you will get an error.
  */

  // class Person is the "companion class" of object Person
  class Person(val name: String, private val age: Int)
  /*
   - This object is now the "companion object" of class Person
   - object that share its name with a class (or trait) in the same source file.
   - Two separate compiled entities (Person the class, Person$ the singleton), linked by the compiler
     because they share a name and a file.
   */
  object Person {
    // 1. companion object can see class Person's private members
    def printAge(p: Person): Unit = println(p.age)
    // 2. if we add this apply, we can create object without new.
    def apply(name: String, age: Int): Person = new Person(name, age)
  }
  val anish = Person("Anish", 30) // internally Person.apply("Anish", 30)

}
