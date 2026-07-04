package scala.udemy.learn.implicits

import scala.language.implicitConversions

/*
  - We will learn how the compiler looks for implicit
  - Various organizing strategies
  - To get the maximum benifit of implicits, without confusing the compiler.
 */
object Organizing extends App {

  /*
  - sorted will take an implicit parameter: (implicit ord: Ordering[Int])
  - which means there is already Ordering for Int is available.
  - Ordering of Int is available via scala.predef package. (automatically imported package)
   */
  val sortedList = List(1, 4, 5, 3, 2).sorted
  println(sortedList) // List(1, 2, 3, 4, 5)

  /*
  1. Implicit type: What can be used as implicit parameters (WHILE CALLING THE METHOD)
    - val / var
    - object
    - accessor method = def with no parenthesis.
  - All of these can be defined only within (class, object, trait)
  - They can not be defined as top-level.
   */

  case class Person(name: String, age: Int)

  val steve = Person("Steve", 30)
  val amy = Person("Amy", 22)
  val john = Person("John", 66)
  val persons = List(steve, amy, john)

  // Compilation error: No implicit found for parameter ord: Ordering[Person]
  implicit val orderByName: Ordering[Person] = Ordering.fromLessThan[Person]((p1, p2) => p1.name < p2.name)

  // sort this by name (alphabetically)
  println(persons.sorted)

  /*
  2. Implicit scope - Places the compiler searches for implicits.
    - normal scope or LOCAL scope: this is basically where we write our code
    - imported scope: can be used if imported
    - companion object of all types involved in the method signature
      - def sorted[B >: A](implicit ord: Ordering[B]): List[B]
      - for this method call, implicits will be searched in
        - List companion object
        - Ordering companion object
        - A and B (all supertype of A) companion object
    - Implicit Priority order: LOCAL SCOPE ----> IMPORTED SCOPE ----> COMPANION OBJECT
    - if not found anywhere then compilation Error.
   */
}
