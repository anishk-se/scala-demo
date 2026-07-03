package scala.udemy.learn.patterns

object MatchPerson extends App {

  // 1. Pattern matching on you class Person
  class Person(val name: String, val age: Int)
  val bob = new Person("Bob", 30)

  object PersonPattern {
    def unapply(person: Person): Option[(String, Int)] = {
      val personTuple = (person.name, person.age)
      Some(personTuple)
    }
  }

  val bobDescription = bob match {
    // Here PersonPattern.unapply(bob) will be called and returned will be used.
    case PersonPattern(n, a) => s"My name is $n and I am $a year old!"
  }
  println(bobDescription)

}
