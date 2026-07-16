package adv.scala.learn.part5_typeclass

object Summoner extends App {

  trait HtmlSerializer[T] {
    def serialize(value: T): String
  }
  object HtmlSerializer {
    def apply[T](implicit serializer: HtmlSerializer[T]): HtmlSerializer[T] = serializer
  }

  case class User (name: String, age: Int, email: String)

  implicit object UserSerializer extends HtmlSerializer[User] {
    override def serialize(user: User): String = {
      s"<div>${user.name}, ${user.age}, <a href=${user.email}></div>"
    }
  }

  val john = User("John", 32, "john@gmail.com")
  println(HtmlSerializer[User].serialize(john))

}
