package scala.udemy.learn.objects

object MethodNotation extends App {

  class Person(val name: String, favoriteMovie: String) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def unary_+ : Person = new Person(name, favoriteMovie) {
      println(s"Hi, I am $name and I like $favoriteMovie")
    }
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
  }

  // 1. infix notation = operator notation (only work with method has single param)
  val anish = new Person("Anish", "Inception")
  println(anish likes "Inception") // equivalent anish.likes("Inception")  - true

  // 2. prefix notation: if method defined with unary_ (only works with - + ~ !)
  val prefix = new Person("Prefix", "Inception")
  val x = +prefix  // equivalent with prefix.unary_-  - Hi, I am Prefix and I like Inception

  // 3. postfix notation (only available with the scala.language.postfixOps import)
  // methods does not receive any parameter, means without ()
  import  scala.language.postfixOps
  val postfix = new Person("Postfix", "Inception")
  println(postfix isAlive) // equivalent with postfix.isAlive  - true

  // 4. apply(): Whenever compiler sees, and instance is being called like function,
  // it will call apply() method
  println(anish()) // equivalent anish.apply()

}
