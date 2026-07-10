package scala.udemy.learn.functions

object CallByValue extends App {

  // Evaluate once
  def callByValue(x: Long): Unit = {
    println("By value: " + x) // By value: 2100208189833
    println("By value: " + x) // By value: 2100208189833
  }

  // Evaluate every time on use
  def callByName(x: => Long): Unit = {
    println("By name: " + x) // By name: 2100378473602
    println("By name: " + x) // By name: 2100378548373
  }

  callByValue(System.nanoTime())
  callByName(System.nanoTime())

}
