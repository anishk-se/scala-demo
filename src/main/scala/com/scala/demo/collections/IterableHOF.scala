package com.scala.demo.collections

object IterableHOF extends App {

  /*
  1. Iteration: def foreach[A](f: A => Unit): Unit = {}
  - Takes a function that accepts one element and returns Unit.
   */
  List(1, 2, 3).foreach(num => print(num))

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

  /*
  5. Aggregation: def foldLeft[B](z: B)(op: (B, A) => B): B
  - The Backpack Analogy 🎒
    - Imagine you're walking from left to right through a list while carrying a backpack.
      - The backpack is the accumulator (acc).
      - Take the acc from your bag and next element of list, process and keep result in bag.
  - That's exactly what foldLeft does.
  - foldLeft(accumulatorInitialValue)((accumulator, element) => accumulatorNewValue)
  - foldRight(accumulatorInitialValue)((element, accumulator) => accumulatorNewValue)
   */
  List("A", "B", "C").foldLeft("")((acc, ele) => acc + ele) // ABC
  List("A", "B", "C").foldRight("")((ele, acc) => ele + acc) // ABC
  List("A", "B", "C").foldRight("")((ele, acc) => acc + ele) // CBA

  /*
  5. Aggregation: def reduceLeft(op: (A, A) => A): A
  - reduceLeft is foldLeft without an initial value.
  - it will throw error on empty element
   */
  List(1, 2, 3, 4, 5).foldLeft(10)((acc, ele) => acc + ele) // 25
  List(1, 2, 3,4, 5).reduceLeft((acc, ele) => acc + ele) // 15
  // List().reduceLeft((acc, ele) => acc + ele) // throw error: missing parameter type

  /*
  6. Grouping: def groupBy[K](f: A => K): Map[K, Iterable[A]]
  - (f: A => K) ==== (f: ele => key)
  - (f: A => K): For each element, what key should it belong to?
    - Think of it as assigning a label (key) to every element (ele).
   */

  // Group by first letter: HashMap(A -> List(Apple, Ant), B -> List(Ball, Bat), C -> List(Cat, Car))
  List("Apple", "Ant", "Ball", "Bat", "Cat", "Car").groupBy(ele => ele.head)

  // Even vs Odd: HashMap(false -> List(1, 3, 5), true -> List(2, 4, 6))
  List(1, 2, 3, 4, 5, 6).groupBy(ele => ele % 2 == 0)

  // Group by length: HashMap(5 -> List(scala), 2 -> List(hi), 3 -> List(cat, dog), 4 -> List(java))
  println(List("hi", "cat", "dog", "scala", "java").groupBy(ele => ele.length))

  // Group employees by department
  // HashMap(HR -> List(Employee(Alice,HR)), IT -> List(Employee(John,IT), Employee(Bob,IT)))
  private case class Employee(name: String, dept: String)
  List(Employee("John","IT"), Employee("Alice","HR"), Employee("Bob","IT")).groupBy(ele => ele.dept)

  /*
  6. Grouping: def groupMap[K, B](key: A => K)(f: A => B): Map[K, Iterable[B]]
  - (key: A => K): assign key/label to the element.
  - (f: A => B): apply this function on every element that is mapped with key.
   */

  // Group by first letter and find length of each word in group
  // Map(A -> List(5, 3), B -> List(4, 3))
  List("Apple", "Ant", "Ball", "Bat").groupMap(ele => ele.head)(ele => ele.length)

  // Group by first letter and convert in upper case
  // Map(A -> List(APPLE, ANT), B -> List(BALL, BAT))
  List("Apple", "Ant", "Ball", "Bat").groupMap(ele => ele.head)(ele => ele.toUpperCase)

  // Find employee name for department
  case class EmployeeDetails(name: String, dept: String, salary: Int)
  val john = EmployeeDetails("John","IT",100)
  val alice = EmployeeDetails("Alice","HR",80)
  val bob = EmployeeDetails("Bob","IT",120)
  // Map(HR -> List(Alice), IT -> List(John, Bob))
  List(alice, john, bob).groupMap(ele => ele.dept)(ele => ele.name)

  /*
  7. Info: sum, min, max, size, isEmpty, nonEmpty
   */
  List(1,2,3,4).size // 4
  List(1,2,3,4).min // 1
  List(1,2,3,4).max // 4
  List(1,2,3,4).isEmpty // false
  List(1,2,3,4).nonEmpty // true
  List(1,2,3,4).sum // 10
}
