package advn.scala.learn.part2_collections

object TuplesHOF extends App {

  val aTuple = (2, "Hello, Scala")
  // new Tuple2[Int, String](2, "Hello, Scala")
  // Tuple2[Int, String](2, "Hello, Scala")
  // Tuple2(2, "Hello, Scala")

  println(aTuple._1) // 2
  println(aTuple.copy(_2 = "Goodbye, Java")) // (2,Goodbye, Java)
  println(aTuple.swap) // (Hello, Scala,2)

}
