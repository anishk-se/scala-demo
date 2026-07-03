package com.scala.demo.wrapper.futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureEx extends App {

  /*
   1. complete immediately
   - Future.successful(value) creates an already-completed successful Future. It does not start a new
     - thread or use an ExecutionContext, making it ideal when you already have the result available.
   */
  def fulfilImmediately(value: String): Future[String] = Future.successful(value)

  /*
  2. Run in Sequence
   */
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] = first.flatMap(_ => second)

  /*
  3. What does Future.sequence do?
   */
  def upperCase(str: String): Future[String] = Future(str.toUpperCase)
  val listOfFuture: List[Future[String]] = List("Anish", "Rahul", "Chandan").map(ele => upperCase(ele))
  val futureOfList: Future[List[String]] = Future.sequence(listOfFuture)

  /*
  4. What does Future.traverse do?
  - List[Future[String]] => Future[List[String].map(function)]
   */
  val traverseList: Future[List[Int]] = Future.traverse(listOfFuture)(ele => ele.map(_.length))

}
