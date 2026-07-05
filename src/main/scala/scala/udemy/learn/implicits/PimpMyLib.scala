package scala.udemy.learn.implicits

object PimpMyLib extends App {

  implicit class RichString(value: String) {
    def stringToInt: Int = Integer.valueOf(value)
  }
  println("10".stringToInt) // 10

  implicit class RichInt(value: Int) {
    def isEven: Boolean = value % 2 == 0
    def isGreaterThan(number: Int): Boolean = value > number
  }

  /*
  - Int class does not have isEven method, but compiler will not GIVE UP
  - can I find something that has isEven method? (YES, RichInt has)
  - can I convert 10 into RichInt? (YES, new RichInt(10))
   */
  println(10.isEven) // new RichInt(10).isEven
  println(11.isEven) // new RichInt(11).isEven
  println(10.isGreaterThan(20)) // new RichInt(10).isGreaterThan(20)

}
