package scala.udemy.learn.generics

object ContraVariance extends App {

  class Animal
  class Dog extends Animal // Dog IS-A Animal
  class Cat extends Animal // Cat IS_A Animal

  // Contra-Variance: support the opposite of inheritance for type T.
  class Trainer[-T]

  val dogTrainer: Trainer[Dog] = new Trainer[Animal]
}
