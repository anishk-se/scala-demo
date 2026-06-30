package com.anishk.ds.collections

object IterableHOF extends App {

  /*
  1. Iteration: def foreach[A](f: A => Unit): Unit = {}
  - Takes a function that accepts one element and returns Unit.
   */
  List(1, 2, 3).foreach(num => println(num))

  /*
  2. Transformation: def map[B](f: A => B): Iterable[B] = {}
  - map takes a function that converts an A into a B, and returns a new Iterable containing
    elements of type B.
  - Although the signature is shown as returning Iterable[B] (because map is defined in the Iterable trait),
    but resulting collections preserve their type.
  - So in practice, map usually returns the same kind of collection with a different element type.
   */
  List(1, 2, 3).map(ele => ele.toString) // returns List[String]
  Set(1, 2, 3).map(ele => ele.toString) // returns Set[String]

  /*
  2. Transformation: def flatMap[B](f: A => IterableOnce[B]): Iterable[B]
  - flatMap takes a function that converts one element into more elements or collection (map), and then
    combines all those results into a single collection (flatten).
  - flatMap = map + flatten
   */
  /*
  1 ───► Set(1, 10)
  2 ───► Set(2, 20)
  3 ───► Set(3, 30)
  flatten and return ------> List(1, 10, 2, 20, 3, 30)
   */
  List(1, 2, 3).flatMap(ele => Set(ele, ele * 10))

  /*
  2. Transformation: def collect[B](pf: PartialFunction[A, B]): Iterable[B]
  - Take a partial function from A to B. Apply it only to the elements for which it is defined, ignore the
    rest, and return the transformed elements.
  - collect = filter + map
   */
  List(1, "A", 3).collect { case ele: Int => ele * 10 }

  /*
  3. Filter: def filter(p: A => Boolean): Iterable[A]
  - filter takes a function that tests each element and returns true or false. It returns a new
    collection containing only the elements for which the function returned true.
  - Notice it returns Iterable[A], not Iterable[B], Because filter doesn't change the element's type.
   */
  List(1, 2, 3, 4).filter(ele => ele % 2 == 0) // List[2, 4]
  List(1, 2, 3, 4).filterNot(ele => ele % 2 == 0) // List[1, 3]

  /*
  3. Filter: def partition(p: A => Boolean): (Iterable[A], Iterable[A])
  - partition takes a predicate (A => Boolean) and splits the collection into two collections:
    - first collection → elements for which the predicate is true
    - second collection → elements for which the predicate is false
  - Notice the return type: (Iterable[A], Iterable[A]) - This is a tuple containing two collections.
   */
  List(1, 2, 3, 4, 5).partition(ele => ele % 2 == 0) // (List(2, 4),List(1, 3, 5))

  /*
  4. Search: def find(p: A => Boolean): Option[A]
  - Search the collection for the first element that satisfies the predicate. If one is found, return
    Some(element) otherwise return None.
   */
  List("Alice", "Bob", "Charlie").find(ele => ele.startsWith("C")) // Some(Charlie)

  /*
  4. Search: def exists(p: A => Boolean): Boolean
  - Does there exist at least one element in the collection that satisfies the predicate?
    - true → if at least one element matches.
    - false → if no elements match.
   */
  List("Alice", "Bob", "Charlie").exists(ele => ele.startsWith("C")) // true

  /*
  4. Search: def forall(p: A => Boolean): Boolean
  - Do all elements in the collection satisfy the predicate?
    - true → if every element satisfies the condition.
    - false → if even one element does not satisfy the condition.
   */
  List("Alice", "Bob", "Charlie").forall(ele => ele.length > 3) // false
}
