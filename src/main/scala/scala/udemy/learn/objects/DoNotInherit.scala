package scala.udemy.learn.objects

object DoNotInherit extends App {

  // 1. final on a class — stop all inheritance
  final class Config(val name: String)
  // compile error: illegal inheritance from final
  // class MyConfig extends Config("x")

  // 2. final on a method or field — stop overriding, but allow inheritance
  class Base {
    final def id: Int = 42 // can't be overridden
    def name: String = "base" // can be overridden
  }

  class Derived extends Base {
    // compile error: overriding final member
    // override def id: Int = 99
    override def name: String = "derived"
  }

  /*
   3. sealed — restrict where subclasses can be defined
   - It means all subclasses must live in the same file
   - Anyone outside that file cannot extend Shape
  */
  sealed trait Shape
  class Circle(radius: Double) extends Shape
  class Square(side: Double) extends Shape

}
