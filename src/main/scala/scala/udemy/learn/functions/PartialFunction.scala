package scala.udemy.learn.functions

object PartialFunction extends App {

  // Int => String
  val aFunction = (x: Int) => x.toString

  // {1, 2} => String
  val aFussyFunction = (x: Int) => {
    if(x == 1) "One"
    else if(x == 2) "Two"
    else throw new MatchError(s"$x is not convertable in Int")
  }

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => "one"
    case 2 => "Two"
    // if no case matches, it will throw scala.MatchError
  }

  // {....} -> is a partial function value, of type PartialFunction
  val aPartialFunction: PartialFunction[Int, String] = {
    case 1 => "one"
    case 2 => "Two"
    // if no case matches, it will throw scala.MatchError
  }
  println(aPartialFunction(2)) // aPartialFunction.apply(2)

  println(aPartialFunction.isDefinedAt(99)) // false

  // return Option, because for values it is not defined return NONE
  val aLiftedFunction: Int => Option[String] = aPartialFunction.lift

  // orElse will take another partial function and more value on top of partial function.
  val chainedPartialFunction = aPartialFunction.orElse[Int, String] {
    case 3 => "Three"
  }

  // partial function extends normal function, so partial function is also a function
  val aTotalFunction: Int => String = {
    case 10 => "Ten"
  }

  val aNormalMappedList = List(1, 2, 3).map {
    x => x match {
      case 1 => "One"
      case 2 => "Two"
      case 3 => "Three"
    }
  }
  // HOF can accept function, so it can also accept partial function
  val aPartialMappedList = List(1, 2, 3).map {
    case 1 => "One"
    case 2 => "Two"
    case 3 => "Three"
  }

  /*
    normal function can have multiple parameter, BUT PARTIAL FUNCTION CAN HAVE ONLY ONE PARAMETER.
   */
}
