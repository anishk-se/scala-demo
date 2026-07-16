package akka.scala.learn.part1_threads

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ThreadingRecap extends App {

  // creating threads on the JVM
  private val aThread = new Thread(() => println("I'm running in parallel"))
  aThread.start() // start a thread
  //----main running-----aThread join-----aThread running-----aThread completed-----main running again
  aThread.join() // wait for a thread to finish

  // different runs produces different results
  private val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  private val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("goodbye")))
  threadHello.start()
  threadGoodbye.start()

  /*
    BankAccount(10000)
    thread1 -> withdraw 1000
    thread2 -> withdraw 2000
    thread1 -> this.amount = 10000 - ..... thread1 is suspended and thread2 takes over
    thread2 -> this.amount = 10000 - 2000 = 8000
    thread1 -> - 1000 = 9000 // thread 1 get chance again
    final this.amount = 9000, but it should be 7000
    so as a result we loose the ATOMICITY of the operation: this.amount = this.amount - money
    ATOMIC = all or nothing
   */
  /*
  To keep the ATOMICITY of the operation, we can use synchronized blocks.
  But the problem is this Bank account example is very simple, but this problem of synchronizing
  thread becomes increasingly difficult as the bigger your application becomes, And in larger
  application this is a huge pain in the neck.
   */
  class BankAccount(private var amount: Int) {
    override def toString: String = "" + amount

    // option 1
    def withdraw(money: Int): Unit = this.synchronized {
      amount -= money
    }

    // option 2
    def safeWithdraw(money: Int): Unit = this.synchronized {
      this.amount -= money
    }
  }

  // scala Future
  import scala.concurrent.ExecutionContext.Implicits.global
  private val future = Future {
    // long computation - on another thread
    42
  }

  future.onComplete {
    case Success(value) => println(s"the meaning of life is $value")
    case Failure(exception) => println(s"I have failed with $exception")
  }

  // Future is a monadic construct, meaning it has map, flatMap, filter.
  val mapFuture = future.map(_ + 1) // Future(43)
  val flatMapFuture = future.flatMap(value => Future(value + 2)) // Future(44)
  // if the predicate is not satisfied, the future will fail with NoSuchElementException
  // otherwise, return Future will be identical to the original future
  private val filterFuture = future.filter(_ % 2 == 0) // Future(42)

  // Since Future support map, flatMap, we can use for-comprehensions
  val aNonsenseFuture = for {
    meaningOfLife <- future
    filteredMeaningOfLife <- filterFuture
  } yield meaningOfLife + filteredMeaningOfLife


}
