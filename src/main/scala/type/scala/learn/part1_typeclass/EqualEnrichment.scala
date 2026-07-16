package adv.scala.learn.part5_typeclass

object EqualEnrichment extends App {

  trait Equal[T] {
    def apply(v1: T, v2: T): Boolean
  }

  case class User(name: String, age: Int, email: String)

  implicit object NameEqualizer extends Equal[User] {
    override def apply(v1: User, v2: User): Boolean = {
      v1.name == v2.name
    }
  }

  implicit class EQUALEnrichment[T](value: T) {
    def ===(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = {
      equalizer.apply(value, anotherValue)
    }
    def !==(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = {
      !equalizer.apply(value, anotherValue)
    }
  }

  val bob = User("Bob", 30, "bob@gmail.com")
  val john = User("John", 30, "john@gmail.com")
  // new EQUALEnrichment[User](bob).===(john)(NameEqualizer)
  println(bob === john) // false
  // new EQUALEnrichment[User](bob).!==(john)(NameEqualizer)
  println(bob !== john) // true

}
