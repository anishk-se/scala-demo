package core.scala.learn.part1_objects

object LazyEvaluation extends App {

  // this will crash the execution with RuntimeException
  // val x: Int = throw new RuntimeException

  // lazy delays the evaluation of values: are evaluated once but only when it is used for the first time.
  lazy val x: Int = throw new RuntimeException

  // evaluated once proof
  lazy val number: Int = { println("evaluating"); 42 }
  println(number) // evaluating 42
  println(number) // 42

  def trueCondition: Boolean = { println("I am true"); true }
  def falseCondition: Boolean = false
  lazy val lazyCondition = trueCondition
  println(if(falseCondition && lazyCondition) "yes" else "no") // no
}
