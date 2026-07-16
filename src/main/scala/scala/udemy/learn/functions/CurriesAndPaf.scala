package scala.udemy.learn.functions

object CurriesAndPaf extends App {

  // curried function: a function is returning another function
  val supperAdder: Int => (Int => Int) = x => (y => x + y)

  val add3: Int => Int = supperAdder(3)
  println(add3(10)) // 13
  println(supperAdder(3)(10)) // 13

  // curried method: belongs to instance
  def curriedAdder(x: Int)(y: Int): Int = x + y

  // you can not call with single parameter
  val add4Normal = curriedAdder(4)(10)

  // ETA expansion: lifting of curried method to curried function
  val add4Curried: Int => Int = curriedAdder(4)
  // Force compiler to apply ETA expansion by adding _
  val add4ETA = curriedAdder(4)_

  // functions(instance of functional type) != methods(belongs to class)
  def inc(x: Int): Int = x + 1 // method
  List(1, 2, 3).map(inc) // List(1, 2, 3).map(x => inc(x)) done by compiler

}
