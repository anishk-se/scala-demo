package core.scala.learn.part2_functions

import scala.annotation.tailrec

object CurriesFunc extends App  {

  val adder: Int => Int => Int = (x: Int) => {
    (y: Int) => x + y
  }

  // adder is a curried function, because it returns another function Int => Int.
  val param1: Int => Int = adder(3)
  println(param1(10)) // 13
  println(adder(3)(10)) // 13

  // Curried part2_functions can also be written with multiple parameter list
  def curriedFormatter(c: String)(x: Double) = c.format(x)

  // must provide the type of standardFormat, otherwise function call will expect both params
  val standardFormat: Double => String = curriedFormatter("%4.2f")
  val preciseFormat: Double => String = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI)) // 3.14
  println(preciseFormat(Math.PI)) // 3.14159265

}
