package scala.udemy.learn.playground.numbers

import scala.annotation.tailrec

object LargestNumber extends App {

  @tailrec
  def insert(value: String, remaining: List[String], left: List[String]): List[String] = {
    remaining match {
      case Nil => (value :: left).reverse
      case head :: tail =>
        if ((value + head) >= (head + value))
          left.reverse ::: (value :: remaining)
        else
          insert(value, tail, head :: left)
    }
  }

  @tailrec
  def sort(remaining: List[String], sorted: List[String]): List[String] = {
    remaining match {
      case Nil => sorted
      case head :: tail =>
        val newSorted = insert(head, sorted, Nil)
        sort(tail, newSorted)
    }
  }

  def largestNumber(nums: List[Int]): String = {

    val result = sort(nums.map(_.toString), Nil).mkString("")

    if (result.forall(_ == '0')) "0"
    else result
  }

  println(largestNumber(List(3,30,34,52,90)))

}