package scala.udemy.learn.generics

object CoVariance extends App {

  class Animal
  class Dog extends Animal // Dog IS-A Animal
  class Cat extends Animal // Cat IS_A Animal

  val animal: Animal = new Dog // this is valid, because Dog IS-A Animal

  // Co-Variance: Support the inheritance for Type T.
  class Cage[+T]

  val dogCage: Cage[Animal] = new Cage[Dog]
  val catCage: Cage[Animal] = new Cage[Cat]

  // this should not happen
  val stringCage: Cage[String] = new Cage[String]

  // T <: Animal -> Animal is an Upper Bound of T.
  // T >: Dog -> Dog is a Lower Bound of T.
  class BetterCage[+T <: Animal]

  val betterDogCage: BetterCage[Animal] = new BetterCage[Dog]
  val betterCatCage: BetterCage[Animal] = new BetterCage[Cat]

  // Type String does not conform to upper bound Animal of type parameter T
  // val betterStringCage: BetterCage[String] = new BetterCage[String]

}
