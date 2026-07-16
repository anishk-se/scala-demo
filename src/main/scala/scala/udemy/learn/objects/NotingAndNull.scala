package scala.udemy.learn.objects

import scala.annotation.tailrec

object NotingAndNull extends App {

  /*
   1. null — instance of the class Null.
   - Null is a subtype of every AnyRef
   - Null is a type with exactly one value: null.
  */
  val s: String = null    // OK, String is an AnyRef
  // val n: Int = null       // compile error — Int is an AnyVal, Null isn't a subtype of it

  /*
   2. Nothing — the type with no values at all
   - Nothing has zero instances — you can never construct a value of type Nothing.
  */
  @tailrec
  def infiniteLoop: Nothing = infiniteLoop
  def fail(msg: String): Nothing = throw new RuntimeException(msg)

}
