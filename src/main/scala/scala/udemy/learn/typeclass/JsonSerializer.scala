package scala.udemy.learn.typeclass

import java.util.Date

object JsonSerializer extends App {

  case class User(name: String, age: Int, email: String)
  case class Post(content: String, createdAt: Date)
  case class Feed(user: User, posts: List[Post])

  sealed trait JSONValue {
    def stringify: String = stringifyWithIndent(0)
    def stringifyWithIndent(indent: Int): String
  }
  final case class JSONString(value: String) extends JSONValue {
    override def stringifyWithIndent(indent: Int): String = "\"" + value + "\""
  }
  final case class JSONNumber(value: Int) extends JSONValue {
    override def stringifyWithIndent(indent: Int): String = value.toString
  }
  final case class JSONArray(values: List[JSONValue]) extends JSONValue {
    override def stringifyWithIndent(indent: Int): String = {
      if (values.isEmpty) "[]"
      else {
        val indentStr = " " * indent
        val nextIndent = indent + 2
        val nextIndentStr = " " * nextIndent
        val formattedValues = values.map(_.stringifyWithIndent(nextIndent))
        formattedValues.mkString("[\n" + nextIndentStr, ",\n" + nextIndentStr, "\n" + indentStr + "]")
      }
    }
  }
  final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue {
    override def stringifyWithIndent(indent: Int): String = {
      if (values.isEmpty) "{}"
      else {
        val indentStr = " " * indent
        val nextIndent = indent + 2
        val nextIndentStr = " " * nextIndent
        val formattedEntries = values.map {
          case (key, value) => nextIndentStr + "\"" + key + "\": " + value.stringifyWithIndent(nextIndent)
        }
        formattedEntries.mkString("{\n", ",\n", "\n" + indentStr + "}")
      }
    }
  }

  implicit class JSONEnrich[T](value: T) {
    def toJson(implicit converter: JSONConverter[T]): JSONValue = converter.convert(value)
  }

  trait JSONConverter[T] {
    def convert(value: T): JSONValue
  }

  implicit object StringConverter extends JSONConverter[String] {
    override def convert(value: String): JSONValue = JSONString(value)
  }
  implicit object NumberConverter extends JSONConverter[Int] {
    override def convert(value: Int): JSONValue = JSONNumber(value)
  }
  implicit object UserConverter extends JSONConverter[User] {
    override def convert(user: User): JSONValue = JSONObject(Map(
      "name" -> JSONString(user.name),
      "age" -> JSONNumber(user.age),
      "email" -> JSONString(user.email)
    ))
  }
  implicit object PostConverter extends JSONConverter[Post] {
    override def convert(post: Post): JSONValue = JSONObject(Map(
      "content" -> JSONString(post.content),
      "createdAt" -> JSONString(post.createdAt.toString)
    ))
  }
  implicit object FeedConverter extends JSONConverter[Feed] {
    override def convert(feed: Feed): JSONValue = JSONObject(Map(
      "user" -> feed.user.toJson,
      "posts" -> JSONArray(feed.posts.map(_.toJson))
    ))
  }

  val now = new Date(System.currentTimeMillis())
  val john = User("john", 34, "john@gmail.com")
  val feed = Feed(john, List(
    Post("Hi", now), Post("Look at this cute puppy", now)
  ))

  println(feed.toJson.stringify)

}
