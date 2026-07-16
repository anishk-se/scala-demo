package adv.scala.learn.part5_typeclass

object HtmlEnrichment extends App {

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  case class User(name: String, age: Int, email: String)

  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = {
      s"<div>${user.name}></div>"
    }
  }

  object LoggedInUserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = {
      s"<div>${user.name}, ${user.age}, <a href=${user.email}></div>"
    }
  }

  implicit class HTMLEnrichment[T](value: T) {
    def toHtml(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  val bob = User("Bob", 30, "bob@gmail.com")
  // new HTMLEnrichment(bob).toHtml(UserSerializer)
  println(bob.toHtml) // <div>Bob></div>
  // new HTMLEnrichment(bob).toHtml(LoggedInUserSerializer)
  println(bob.toHtml(LoggedInUserSerializer)) // <div>Bob, 30, <a href=bob@gmail.com></div>

}
