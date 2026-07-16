package core.scala.learn.part1_objects

object AnonymousClass extends App {

  abstract class Animal {
    def eat(): Unit
  }

  /*
    it is an extending syntax sugar for extending and instantiating a class.
   */
  val funnyAnimal = new Animal {
    override def eat(): Unit = println("My funny Animal......")
  }
  println(funnyAnimal.getClass)

  class AnonymousClass$$anon$1 extends Animal {
    override def eat(): Unit = println("compiler's funny Animal............")
  }
  val compilerFunnyAnimal = new AnonymousClass$$anon$1

}
