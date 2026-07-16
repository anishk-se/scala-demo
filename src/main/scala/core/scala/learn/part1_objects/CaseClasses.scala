package core.scala.learn.part1_objects

/*
 often for light-weight classes, we need ti implement all boilerplate,
 like companion object, serialization, toString for pretty print, equals, hashcode and so on.
 case class are ideal solution to this problem.
 case classes are exceptionally useful shorthand for defining class and companion object and a lot
 of sensible defaults in one go.
  */
object CaseClasses extends App {

   case class Person(name: String, age: Int)

   // 1. class parameters are promoted to fields
   val jim = new Person("jim", 30)
   println(jim.age) // 30

   // 2. sensible toString
   println(jim) // Person(jim,30)

   // 3. equals and hashCode implemented, so that == for two case class instance with same parameters
   // returns true.
   val jim2 = new Person("jim", 30)
   println(jim == jim2) // true

   // 4. case classes has handy copy methods
   val jim3 = jim.copy()
   val jim4 = jim.copy(age = 40)

   // 5. case classes have companion object's apply method to do the same thing as the constructor
   val mary = Person("marry", 50) // So in practice we do not really use the keyword new.

   // 6. case classes are serializable, which makes case classes really useful to dealing with
   // distributed system like AKKA.

   // 7. case classes have extractor part3_patterns - case classes can be used in pattern matching.






}
