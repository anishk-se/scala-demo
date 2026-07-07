package scala.udemy.learn.generics

object Basics extends App {

  class MyClass[T](ele1: T, ele2: T) {
    // T can be used Anywhere
  }

  trait MyTrait[T] {
    // T can be used Anywhere
  }

  def MyFunction[T](value: T): T = ???

  // Rule of Thumb: Wherever you see a declaration like ...[T], you're introducing a type parameter and
  // will be available in scope to use.

  // Normal rule to use: whenever you use .....[T] you need to replace .....[String(Your real type)]
  // but for function we have Syntax sugar
  def print[T](value: T): Unit = println(value)
  print(10) // Compiler can infer T from arguments

}
