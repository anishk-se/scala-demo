package adv.scala.learn.part5_typeclass

/*
 A type class is a design pattern that allows you to add behavior to existing types without modifying
 them or using inheritance. It promotes:
  - Separation of concerns: Divide a program into independent parts, where each part has a single,
    well-defined responsibility.
    - Example: User only stores user data (name, age, email), while HTMLSerializer[User] is responsible
      for converting a User into HTML.
  - Supports multiple implementations for the same type: Allows multiple implementations of the same
    behavior for a type without modifying the type itself.
    Example:
      - UserSerializer → renders only the user's name.
      - LoggedInUserSerializer → renders the name, age, and email.
    - Both serialize a User, but in different ways.
  - Works with third-party classes: Enables you to add new behavior to classes from external libraries
    without changing or inheriting from them.
  - Follows the Open/Closed Principle: Software should be open for extension but closed for
    modification—new functionality is added by creating new implementations instead of changing existing
    code.
 */
object TypeClasses extends App {

  /*
  1. Inheritance
   */
  trait HTMLWritable {
    def toHtml: String
  }

  case class User (name: String, age: Int, email: String) extends HTMLWritable {
    // So we have basically converted User in to an HTML component, to render.
    override def toHtml: String = s"<div>$name, $age, <a href=$email></div>"
  }
  println(User("John", 32, "john@gmail.com").toHtml)

  /*
  2. A TYPE CLASS: HTMlSerializer[T]
  - specifies a set of operation -  in this case serialize
  - That can be applied to a given type T.
  - Anyone who extends HTMlSerializer need to provide this functionality.
  - All the implementers of TYPE CLASS are called TYPE CLASS INSTANCE.
  - Even though the type class instances are types, such as UserSerializer, LoggedInUserSerializer
   - but they are called instances because it does not make sense instantiate multiple times.
   - that's why we often use singleton object for them.
   */

  trait HTMlSerializer[T] {
    def serialize(value: T): String
  }

  val john = User("John", 32, "john@gmail.com")

  object UserSerializer extends HTMlSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name}</div>"
  }
  println(UserSerializer.serialize(john)) // <div>John</div>

  object LoggedInUserSerializer extends HTMlSerializer[User] {
    override def serialize(user: User): String = {
      s"<div>${user.name}, ${user.age}, <a href=${user.email}></div>"
    }
  }
  println(LoggedInUserSerializer.serialize(john)) // <div>John, 32, <a href=john@gmail.com></div>

}
