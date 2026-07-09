package scala.udemy.learn.objects

object Exceptions extends App {

  // 1. throwing an Exception
  throw new NumberFormatException // there is nobody to catch it, it will crash the program.

  /*
   2. Like everything else in scala `throwing an exception` is an expression of type Nothing.
   - So `throwing an exception` does not hold the value but, can be assigned.
   */
  val aWeiredValue: Nothing = throw new NullPointerException

  /*
  3. class to be used with throw, must be a child of `Throwable`.
   - Throwable has two predefined child: Exception and Error
   - Exception denotes something wrong with the program.
   - Error denotes something wrong with the system ex: StackOverflow
   */

  def stringToInt(value: String, withException: Boolean): Int = {
    if(withException) throw new RuntimeException(s"value = $value can not be converted to Int!")
    else value.toInt
  }

  /*
   4. Catching an exception
   - AnyVal because => Int, Unit ---unify---> AnyVal
   */
  val aPotentialFailure: AnyVal = try {
    stringToInt("10", withException = true) // returning Int
  } catch {
    case e: RuntimeException => println(e.getMessage) // returning Unit
  } finally {
    // finally does not affect the return type and value of the try-catch expression.
    println("we have called stringToString!") // we have called stringToString!
  }

  // 5. How to define your Exception
  class MyException extends Exception

}
