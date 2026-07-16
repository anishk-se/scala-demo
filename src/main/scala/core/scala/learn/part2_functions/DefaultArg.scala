package core.scala.learn.part2_functions

import scala.annotation.tailrec

object DefaultArg extends App {

  @tailrec
  def factorial(n: Int, acc: Int = 1): Int = {
    if(n <= 1) acc
    else  factorial(n-1, acc * n)
  }

  println(factorial(3))

  def connect(host: String, port: Int = 8080, timeout: Int = 30): Unit = {
    println(s"$host:$port, timeout=$timeout")
  }
  connect(host = "localhost", 5432)
  connect("localhost") // localhost:8080, timeout=30
  connect("localhost", 9090) // localhost:9090, timeout=30
  connect("localhost", timeout = 60) // localhost:8080, timeout=60   <- named arg skips port

}
