package scala.udemy.learn.generics

object InVariance extends App {

  class Animal
  class Dog extends Animal // Dog IS-A Animal
  class Cat extends Animal // Cat IS_A Animal

  val animal: Animal = new Dog // this is valid, because Dog IS-A Animal

  // Invariance Type: No Support of Inheritance for type T
  class DogShop[T]
  val dogCage: DogShop[Dog] = new DogShop[Dog]
}
