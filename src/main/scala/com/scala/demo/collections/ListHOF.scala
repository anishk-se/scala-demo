package com.scala.demo.collections

object ListHOF extends App {

  /*
  1. Creating: List()
   */
  val list = List(1, 2, 3)

  /*
  1. Creating: Nil
  - Empty List
   */

  /*
  1. Creating: List.from
   */
  List.from(list)

  /*
  2. Adding: ::
  - Adding at the place of head
   */
  0 :: list

  /*
  2. Adding: +:
  - add at head (read like + <- :)
   */
  0 +: list

  /*
  2. Adding: :+
  - add at last (read like : -> +)
   */
  list :+ 4

  /*
  2. Adding: patch(afterPosition, list, numberOfElementsToDelete)
  - Insert at given index (position will start from - 1)
   */
  val inCompleteList = List(1, 2, 3, 5, 6)
  inCompleteList.patch(3, List(4), 0) // List(1, 2, 3, 4, 5, 6)

  /*
  3. Updating: updated(afterIndex, newValue)
  - update at given index (index will start from - 0)
   */
  val wrongList = List(1, 2, 3, 10, 5, 6)
  wrongList.updated(3, 4) // List(1, 2, 3, 4, 5, 6)

  /*
  4. Reading: head, last, tail
  - tail means every thing after first element
  - List(1).tail - it will return Nil
   */
  list.head // 1
  list.headOption
  list.tail // List(2, 3)
  list.last // 3
  list.lastOption

  /*
  4. Reading: apply, lift
   */
  // list(5) // java.lang.IndexOutOfBoundsException
  println(list(2)) // 3
  println(list.lift(5)) // None

  /*
  5. Deleting: drop(tillPosition)
   */
  val tempList = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  tempList.drop(2) // List(2, 3, 4, 5, 6, 7, 8, 9)

  /*
  6. Slicing: take(tillPosition)
   */
  tempList.take(2) // List(0, 1)

  /*
  6. Slicing: slice(afterPosition, tillPosition)
   */
  tempList.slice(3, 7) // List(3, 4, 5, 6)

  /*
  6. Slicing: splitAt(atPosition)
  - till this position first list, after this position second list
   */
  tempList.splitAt(4) // (List(0, 1, 2, 3),List(4, 5, 6, 7, 8, 9))

  /*
  6. Slicing: span(predicate)
  - Take the element till predicate returns true
   */
  tempList.span(_ < 6) // (List(0, 1, 2, 3, 4, 5),List(6, 7, 8, 9))

  /*
  7. Combination: zip, unzip, zipwithIndex
   */
  val myList1 = List(1, 2, 3, 4, 5, 6)
  val myList2 = List("a", "b", "c")

  val zipList = myList1.zip(myList2) // List((1,a), (2,b), (3,c))

  zipList.unzip // (List(1, 2, 3),List(a, b, c))

  println(myList2.zipWithIndex) // List((a,0), (b,1), (c,2))
}
