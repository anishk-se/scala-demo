package scala.udemy.learn.oops

object Packaging extends App {

  // 1. members from same package can be access by their simple name
  val exceptions = Exceptions

  // 2. members from outside package we need to import, or fully qualified name

  import scala.udemy.learn.collections.ListHOF
  val listHOF = ListHOF

  // 3. we can create a package object, one per package and will be visible in entire package

  // 4. name aliasing during import.

  import scala.udemy.learn.collections.{MapHOF => MyMap}
}
