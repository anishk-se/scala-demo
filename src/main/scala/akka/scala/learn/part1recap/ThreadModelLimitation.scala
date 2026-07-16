package akka.scala.learn.part1recap

object ThreadModelLimitation extends App {

   // 1. OOP encapsulation is only valid in the SINGLE THREAD MODEL
   private class BankAccount(private var amount: Int) {

     def getAmount: Int = this.amount

     override def toString: String = "" + this.amount

     def withdraw(money: Int): Unit = this.amount -= money

     def deposit(money: Int): Unit = this.amount += money

   }

   private val account = new BankAccount(2000)

   for(_ <- 1 to 10000) {
     new Thread(() => account.withdraw(1)).start()
   }

   for (_ <- 1 to 10000) {
     new Thread(() => account.deposit(1)).start()
   }

   /*
   the balance is 2003 and should be 2000
   OOP encapsulation is broken, we can use synchronized locks to rescue.
   But synchronized introduces new problems, like deadlocks, livelocks, starvation, etc.
   Good luck with distributed data structure or resources, because locking those is
   order of magnitude slower.
   Required: Need a data structure which is fully encapsulated with no locks.
    */
   println(s"the balance is ${account.getAmount} and should be 2000")

   // 2. delegating something to a thread is a PAIN ()
  // A thread which is already running, and you want to supply more information to it.

}
