package scala.udemy.learn.wrapper.futures

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object BlockingFuture extends App {

  val txnStatus = BankingApp.purchase("Anish", "Laptop", "FlipKart", 50000)
  println(txnStatus)
}

object BankingApp {
  def fetchUser(name: String): Future[User] = Future {
    Thread.sleep(500) // simulate fetching from the DB
    User(name)
  }
  def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
    Thread.sleep(1000) // simulate some processes
    Transaction(user.name, merchantName, amount, "SUCCESS")
  }
  def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
    val transactionStatusFuture = for {
      user <- fetchUser(username)
      transaction <- createTransaction(user, merchantName, cost)
    } yield transaction.status
    // if future did not complete in 2 second, it will throw time out exception
    Await.result(transactionStatusFuture, 2.seconds)
  }
}

case class User(name: String)
case class Transaction(sender: String, receiver: String, amount: Double, status: String)
