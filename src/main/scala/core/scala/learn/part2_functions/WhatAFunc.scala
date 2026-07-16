package core.scala.learn.part2_functions

object WhatAFunc extends App {

  trait MyFunction[A, B] {
    def apply(ele: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(ele: Int): Int = ele * 2
  }
  // we can call doubler like function, it acts like a function.
  println(doubler(2)) // doubler.apply(2)

  // So scala has predefined trait like MyFunction, which Companions can be called like function.
  // Function1[A, B], Function2[A, B, C].......Function22[......]

  val stringToInt = new Function1[String, Int] {
    override def apply(ele: String): Int = ele.toInt
  }
  println(stringToInt("3") + 4) // 7

  // Function2[Int, Int, Int] ====== Function2[Parameter1, Parameter2, ReturnType]
  // Function[Int, Int, Int] ====== (Int, Int) => Int (Syntax sugar)
  val adder: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  println(adder(10, 20)) // 30

  // ALL part2_functions are instances of classes DERIVED from functional types (Function1, Function2)

  // Higher order function: Can take function as an argument, or return function as result
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function[Int, Function1[Int, Int]] {
    override def apply(v1: Int): Function1[Int, Int] = new Function[Int, Int] {
      override def apply(v2: Int): Int = v1 + v2
    }
  }

  val resultSuperAdder: Function1[Int, Int] = superAdder(3)
  println(resultSuperAdder(4)) // 7

  // Curried function: Curried part2_functions have the property, that they can be called with
  // multiple parameter list, just by their mere definition. So a curried function actually takes
  // some kind of parameter and returns another function which receives parameter.
  val curriedSuperAdder = superAdder(3)(4) // superAdder.apply(3).apply(4)

  val convertStringToIntVal: String => Int = (ele: String) => ele.toInt
  def convertStringToIntDef: String => Int = (ele: String) => ele.toInt

  // Rule of thumb: use val when you want a value computed once and reused; use def when you want a recipe
  // that may need to run fresh each time
  println(convertStringToIntVal("10"))

  val f: String => Int = convertStringToIntDef   // Step 1: call the def, get back a Function1 object
  val result: Int = f("42")  // Step 2: call that function with ele = "42"
  println(convertStringToIntDef("10"))
}
