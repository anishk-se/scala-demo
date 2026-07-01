package com.scala.demo.wrapper

/*
 An Option is a wrapper for value that might be present or not.
 - Option has the following two child classes:
   - case class Some(ele) => Some wraps a concrete value
   - case object None => None is a singleton for absence value.
 - Lot of functions from collection, deals with Option.
 */
object Options extends App {

  // 1. Creating Option
  val myFirstOption: Option[Int] = Some(4)
  // None has a type: Option[Nothing]
  val noOption: Option[Nothing] = None

  // 2. Option's companion apply() method
  def unSafeMethod(): Null = null
  // apply method will take care to build a Some(value) or None, depending on the value passed.
  // We never do a null checks ourselves, Option.apply() will take care of it.
  val result = Option(unSafeMethod())

  // 3. Fallback to safe method call
  def backupMethod() = "A valid result!"
  // orElse() will be called if Option(unSafeMethod()) return None.
  val validResult = Option(unSafeMethod()).orElse(Option(backupMethod()))

  // 4. isEmpty, get functions
  println(myFirstOption.isEmpty) // true if None
  //println(noOption.get) // Unsafe - None.get is a NoSuchElementException
  println(noOption.getOrElse("Empty")) // Safe to call

  def mayBeNone(isNone: Boolean): Option[String] = {
    if(isNone) None
    else Some("A valid result!")
  }

  def upperCase(string: String): Option[String] = {
    if(string.isEmpty) None
    else Some(string.toUpperCase)
  }

  /*
  5. map, flatMap, filter
  - If the Option contains a value (Some), apply the function and wrap the result back in Some.
  - If it is None, do nothing and return None.
   */
  val noneMapResult = mayBeNone(true).map(ele => ele.toUpperCase)
  println(noneMapResult) // None
  val someMapResult = mayBeNone(false).map(ele => ele.toUpperCase)
  println(someMapResult) // Some(A VALID RESULT!)

  // if your function is returning Option use flatMap
  val noneFlatMapResult = mayBeNone(true).flatMap(ele => upperCase(ele))
  println(noneFlatMapResult) // None
  val someFlatMapResult = mayBeNone(false).flatMap(ele => upperCase(ele))
  println(someFlatMapResult) // Some(A VALID RESULT!)

  val noneFilterResult = mayBeNone(true).filter(ele => ele.length > 20)
  println(noneFilterResult) // None
  val someWithNoValueFilterResult = mayBeNone(false).filter(ele => ele.length > 20)
  println(someWithNoValueFilterResult) // None
  // keep the value only if predicate is true
  val someFilterResult = mayBeNone(false).filter(ele => ele.length > 5)
  println(someFilterResult) // Some(A valid result!)

  // for-comprehension
  val firstName = Some("Anish")
  val lastName  = Some("Kumar")
  // You want a result Some("Anish Kumar")
  val fullNameFlatMapMap = firstName.flatMap(first => lastName.map(last => s"$first $last"))
  println(fullNameFlatMapMap)
  val fullNameForComprehension = for {
    first <- firstName // Take value from option1
    last <- lastName // Take value from option2
  } yield s"$first $last"
  println(fullNameForComprehension)

  val ageOfAnish = Some(10)

  // if any value is None, result will be None.
  val isEligibleVoter = for {
    first <- firstName
    last <- lastName
    age <- ageOfAnish
    if age > 18 // if guard is a filter function, filter return None is predicate is false.
  } yield s"$first $last can vote"
  println(isEligibleVoter)

}
