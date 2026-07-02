package com.scala.demo.wrapper

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/*
 - A Future is a wrapper for asynchronous computation that will complete with either a successful result
   or a failure in the future.
 - ExecutionContext: It manages the pool of threads, on which future runs.
 */
object Futures extends App {

//  // 1. creating future
//  val myFuture: Future[String] = Future {
//    "code will come here, that needs to run on separate thread"
//  } // (global) which is passed by compiler as implicit
//
//  /*
//  2. Get the value
//   - Future State: Not Complete, Success, Failure
//   - Return type of value -> Option[Try[Customer]]
//   - Option - to handle if not completed, Try -> if completed, then success or fail.
//   */
//  println(myFuture.value)
//
//  /*
//   3. Wait for future to complete - onComplete
//   - Get the value of the future when the future actually completes.
//   - Future complete with Try: because it can Success or Failure
//   - onComplete returns the Unit, so use it for side effect.
//   - onComplete is a callback, it says Execution context if customerDetails future completes then,
//     please execute me.
//   - Execution context will decide, when and on which thread call back will be executed.
//   */
//  // Success example
//  Future(BankDatabase.getCustomerByName("Anish")).onComplete {
//    case Success(cust) => println(s"[Success]: name = ${cust.name} and id = ${cust.id}")
//    case Failure(exception) => println(s"[Failure]: ${exception.getMessage}")
//  }
//  // Failure example
//  Future(BankDatabase.getCustomerByName("Deepak")).onComplete {
//    case Success(cust) => println(s"[Success]: name = ${cust.name} and id = ${cust.id}")
//    case Failure(exception) => println(s"[Failure]: ${exception.getMessage}")
//  }
//
//  // 4. nested onComplete
//  Future(BankDatabase.getCustomerByName("Anish")).onComplete{
//    case Success(cust) => Future(BankDatabase.getBankAccountById(cust.id)).onComplete{
//      case Success(acc) => println(s"[Success]: name = ${cust.name} and balance = ${acc.balance}")
//      case Failure(exception) => println(s"[Failure]: ${exception.getMessage}")
//    }
//    case Failure(exception) => println(s"[Failure]: ${exception.getMessage}")
//  }
//
//  /*
//   5. Functional callback - map, flatMap, filter
//   - If the Future contains a Success(value), apply the function and wrap the result back in the Future.
//   - If it contains Failure(exception), do nothing and return Failure(exception)
//   - If predicate is failed, filter will return Failure. you will get NoSuchElementException
//   */
//  // Success
//  Future(BankDatabase.getCustomerByName("Anish"))
//    .map(cust => cust.id)
//    .foreach(id => println(s"[Success]: id = $id")) // id = 1 --- also a side effect call back
//  // Failure
//  Future(BankDatabase.getCustomerByName("Deepak"))
//    .map(cust => cust.name)
//    .foreach(id => println(s"[Failure]: id = $id"))
//
//  /*
//   6. for-comprehension
//   - If any future fails, it will return the Failure(exception)
//   */
//  val result: Future[String] = for {
//    cust <- Future(BankDatabase.getCustomerByName("Bipin"))
//    acc <- Future(BankDatabase.getBankAccountById(cust.id))
//  } yield s"[For-Comprehension]: account balance is: ${acc.balance}"
//
//  result.onComplete{
//    case Success(value) => println(result)
//    case Failure(ex) => println(ex.getMessage)
//  }
//  /*
//  7. recovery callbacks: recover and recoverWith
//  - Think of recover and recoverWith as the error-handling counterparts of map and flatMap.
//  -    | Success Path | Failure Path  |
//       | ------------ | ------------- |
//       | `map`        | `recover`     |
//       | `flatMap`    | `recoverWith` |
//   - For code writing think, recover and recoverWith is a catch with try
//   */
//  Future (6 / 0) recover { case e: ArithmeticException => 0 }
//  Future (6 / 0) recoverWith { case e: ArithmeticException => Future { Int.MaxValue } }

  /*
  8. Blocking future: Sometime it make sense to block the future
   */

  Thread.sleep(20000) // Hold main thread, otherwise it will complete before future and exit.
}

object BankDatabase {

  val anish = Customer(1, "Anish")
  val rahul = Customer(2, "Rahul")
  val chandan = Customer(3, "Chandan")
  val bipin = Customer(4, "Bipin")

  val anishAccount = BankAccount(1, 5000)
  val rahulAccount = BankAccount(2, 8500)
  val chandanAccount = BankAccount(3, 12000)

  val customerTable: List[Customer] = List(anish, rahul, chandan, bipin)
  val accountTable: List[BankAccount] = List(anishAccount, rahulAccount, chandanAccount)

  def getCustomerByName(name: String): Customer = {
    println(s"[DB] Reading customer $name from customerTable")
    Thread.sleep(3000)
    customerTable.find(cust => cust.name == name) getOrElse  {
      throw new RuntimeException(s"user with name = $name not found!")
    }
  }

  def getBankAccountById(custId: Int): BankAccount = {
    println(s"[DB] Reading bank account for customer $custId")
    Thread.sleep(2000)
    accountTable.find(_.customerId == custId).getOrElse {
      throw new RuntimeException(s"Account not found for customer $custId")
    }
  }

}
case class Customer(id: Int, name: String)
case class BankAccount(customerId: Int, balance: Double)