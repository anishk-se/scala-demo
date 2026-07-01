package com.scala.demo.playground.numbers

import scala.annotation.tailrec

object CheckPrime extends App {
  /*
  checkPrime: It will take the number and return Boolean:
    - true: If number is prime
    - false: If number is not prime
  Logic: Need to check prime till number >= (divisor * devisor) and
     - count > 1 means divided by more than one number -> false
   */
  @tailrec
  private def checkPrime(number: Int, acc: Int = 1, count: Int = 0): Boolean = {
    if(count > 1) false
    else if(number >= acc * acc) {
      if(number % acc == 0)
        checkPrime(number, acc + 1, count + 1)
      else
        checkPrime(number, acc + 1, count)
    } else true
  }

  (1 to 100).foreach(number =>
    println(s"IS number $number prime? " + checkPrime(number))
  )
}
