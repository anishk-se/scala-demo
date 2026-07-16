package core.scala.learn.part4_wrapper

import scala.util.{Failure, Success, Try}

object HandlingFailures extends App {

  // 1. creating success and failure
  val aSuccess: Try[String] = Success("I am success")
  println(aSuccess) // Success(I am success)

  val aFailure: Try[Nothing] = Failure(new RuntimeException("I am failed"))
  println(aFailure) // Failure(java.lang.RuntimeException: I am failed)

  def unSafeMethod(): Nothing = throw new RuntimeException("I am unSafe and FAILED!")

  // 2. Do not construct: Most of the time you do not need to construct Success and Failure by
  // ourselves, because Try companion object's apply method will take care of that.
  val potentialFailure = Try(unSafeMethod())
  println(potentialFailure) // Failure(java.lang.RuntimeException: I am unSafe and FAILED!)

  // 3. Syntax Sugar - Most of the time you will be using this
  val anotherPotentialFailure = Try {
    unSafeMethod()
  }

  // 4. check if it is success or Failed
  println(anotherPotentialFailure.isSuccess)
  println(anotherPotentialFailure.isFailure)

  // 5. orElse Method
  val betterUnsafeMethod = Failure(new RuntimeException)
  val betterSafeMethod = Success("A valid result")
  val aBetterFallback = betterUnsafeMethod orElse betterSafeMethod

  /*
   6. map, flatMap and filter
   - If the Try contains a Success(value), apply the function and wrap the result back in Success.
   - If it contains Failure(exception), do nothing and return Failure(exception)
   */
  val mapSuccessResult = Try("100".toInt).map(ele => s"$ele Converted in Int")
  println(mapSuccessResult) // Success(100 Converted in Int)
  val mapFailureResult = Try("hundred".toInt).map(ele => s"$ele not converted in Int")
  println(mapFailureResult) // Failure(java.lang.NumberFormatException: For input string: "hundred")

  def tryThis(value: String): Try[String] = {
    if(value.contains("Converted")) Success(value)
    else Failure(new NumberFormatException("Again failed"))
  }

  // if your method is returning Try then use flatMap.
  val flatMapSuccessResult = Try("100".toInt).flatMap(ele => tryThis(s"$ele Converted in Int"))
  println(flatMapSuccessResult) // Success(100 Converted in Int)
  val flatMapFailedResult = Try("100".toInt).flatMap(_ => tryThis("this is failed"))
  println(flatMapFailedResult) // Failure(java.lang.NumberFormatException: Again failed)

  // If predicate is failed, filter will return Failure.
  val filterResult = Success(100).filter(ele => ele > 200)
  println(filterResult) // Failure(java.util.NoSuchElementException: Predicate does not hold for 100)
}
