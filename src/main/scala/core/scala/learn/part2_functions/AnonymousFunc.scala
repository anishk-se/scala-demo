package core.scala.learn.part2_functions

import scala.annotation.tailrec

object AnonymousFunc extends App {

  val doubler = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 * 2
  }

  // Anonymous function or lambda (think like implementing above apply method)
  // apply(v1: Int): Int = v1 * 2 === (v1: Int) => v1 * 2
  // (v1: Int): Int => v1 * 2 (since apply has :Int as return type, but here we can not write)
  // just parameter and implementation of apply, that's it.
  val betterDoubler = (v1: Int) => v1 * 2

  // We have defined type of betterDoubler2 so compiler can infer the type for v1 parameter.
  val betterDoubler2: Int => Int = v1 => v1 * 2

  // multiple params in lambda
  val adder = (v1: Int, v2: Int) => v1 + v2
  val adder2: (Int, Int) => Int = (v1, v2) => v1 + v2

  // no params in lambda
  val doSomething = () => "I am no param lambda"
  println(doSomething) // AnonymousFunc$$$Lambda$7/824009085@7e0babb1
  // Because lambda is an instance of Function type, and we must need to call apply
  println(doSomething()) // I am no param lambda

  // More syntax sugar, underscore can hold parameters, but compiler must know the type for _.
  val increment = (ele: Int) => ele + 1
  val niceIncrement: Int => Int = _ + 1

  val subtract = (ele1: Int, ele2: Int) => ele1 - ele2
  val niceSubstrate: (Int, Int) => Int = _ - _

  // Write a function that apply another function, n times on a given value x
  val plusOne = (x: Int) => x + 1

  @tailrec
  def nTimes(function: Int => Int, numberOfTimes: Int, value: Int): Int = {
    if (numberOfTimes <= 0) value
    else nTimes(function, numberOfTimes - 1, function(value))
  }
  println(nTimes(plusOne, 3, 10)) // 13
  
}