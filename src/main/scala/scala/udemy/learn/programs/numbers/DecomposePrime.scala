package scala.udemy.learn.programs.numbers

import scala.annotation.tailrec

object DecomposePrime extends App {
  /*
  36 % 2 = 0, remaining => 36 / 2 = 18, result = [2]
  18 % 2 = 0, remaining => 18 / 2 = 9, result = [2, 2]
  9 % 2 = ?, remaining => 9, result = [2, 2]
  9 % 3 = 0, remaining => 9 / 3 = 3, result = [3, 2, 2]
  3 % 3 = 0, remaining => 3 / 3 = 1, result = [3, 3, 2, 2]
  number == 1 return result.
   */
  @tailrec
  private def decomposePrime(number: Int, cDivisor: Int = 2, acc: List[Int] = Nil): List[Int] = {
    if(number == 1) acc
    else if(number % cDivisor == 0)
      decomposePrime(number / cDivisor, cDivisor, cDivisor :: acc)
    else if (cDivisor * cDivisor > number) // number is prime
      number :: acc
    else decomposePrime(number, cDivisor + 1, acc)
  }

  println(decomposePrime(36))
  println(decomposePrime(19))
  println(decomposePrime(1))
}
