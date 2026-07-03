package scala.udemy.learn.patterns

object AllPatterns extends App {

  val x: Any = "Scala"
  case object Person
  object Anish

  // 1. pattern on constants (case object and object is a constant in pattern matching)
  val constant = x match {
    case 1 => "a number"
    case "Scala" => "The Scala"
    case true => "The truth"
    case Person => "The case object"
    case Anish => "The Singleton"
    case _ => "Match Anything"
  }
  println(constant)

  // 2. match anything
  val aMatchAnything = x match {
    case _ => "match anything with wild card"
  }

  val aMatchAnythingWithVariable = x match {
    case anything => s"It can match anything $anything"
  }

  // 3. match tuples
  val aTupleMatch = (1, 2) match {
    case (1, 2) => "match tuple of (1, 2)"
    case (anything, 2) => s"match tuple of ($anything, 2)"
    case (anything1, anything2) => s"match tuple of ($anything1, $anything2)"
  }

  // 4. match list
  val listMatching = List(1, 2, 3, 4) match {
    case List(1, _, _, _, _) => s"match list start with 1"
    case List(1, 2, _*) => "match list start with 1, 2 and then any number of element"
  }

  val unknown: Any = 2
  // 5. match the type
  val typeMatching = unknown match {
    case list: List[Int] => s"It will match if type is List[Int]"
    case _ => "It will match anything"
  }

  // 6. Give a name to pattern
  val namedPattern = List(1, 2) match {
    case Nil => "List is empty"
    case nonEmptyList @ List(_*) => s"head is ${nonEmptyList.head} and tail is ${nonEmptyList.tail}"
  }
  println(namedPattern)

  // 7. multi pattern
  val multiPattern = List(1, 2) match {
    case Nil | List(0) => s"this is empty list"
    case nonEmptyList @ List(_*) => s"${nonEmptyList.head} and ${nonEmptyList.tail}"
  }

  // 8. if guards
  val ifGuards = List(1, 2, 3) match {
    case List(_, secondElement, _) if secondElement % 2 == 0 => s"second element $secondElement is even"
  }

  // 9. issue with generic type
  val genericIssue = List(1, 2) match {
    // After compilation this will be case listOfString: List
    case listOfString: List[String] => "List of String"
    // After compilation this will be listOfInt: List
    case listOfInt: List[Int] => "List of Int"
  } // So it will return "List of String"
}
