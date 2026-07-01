package com.scala.demo.collections

object SetHOF extends App {

  /*
  1. Creation: Set(elems: A)
   */
  val fruits = Set("Apple", "Banana", "Orange")

  /*
  1. Creating: Set.empty[Type]
   */
  val emptySet = Set.empty[Int]

  /*
  1. Creating: Set.from(source: IterableOnce[A])
  - Since the source is IterableOnce, you can pass any collection of A.
   */
  println(Set.from(Map("Anish" -> 30))) // Set((Anish,30))

  /*
  2. Adding: +
  - Add a single element
   */
  emptySet + 10

  /*
  2. Adding: def ++(elems: IterableOnce[A]): Set[A]
  - Add multiple elements
   */
  fruits ++ List("Guava", "Grapes", "Apple")

  /*
  3. Updating: No method
  - There is NO "update" method for Set.
  - A Set stores only values, not positions or key-value pairs.
  - How do we "update" a Set? Remove the old element and add the new element.
   */

  /*
  4. Reading: "Read" means checking, does the element exists or not.
  - will return true or false
   */
  fruits("Pineapple")
  fruits.contains("Apple")

  /*
  5. Deleting: -
  - delete a single element, if not found no action (No exception)
   */
  fruits - "apple"

  /*
  5. Deleting: --
  - delete multiple elements, if any element is not found then no action (No Exception)
   */
  fruits -- List("Apple", "Orange")

  /*
  6. Union: set1 union set2
  - set1 = {1, 2, 3, 4}, set2 = {3, 4, 5, 6}
  - combine both set = {1, 2, 3, 4, 3, 4, 5, 6}
  - Remove duplicates = {1, 2, 3, 4, 5, 6}
   */
  val set1 = Set(1, 2, 3, 4)
  val set2 = Set(3, 4, 5, 6)

  set1 union set2

  /*
  7. Intersection: set1 intersect set2
  - set1 = {1, 2, 3, 4}, set2 = {3, 4, 5, 6}
  - combine both set = {1, 2, 3, 4, 3, 4, 5, 6}
  - Keep duplicates only = {3, 4, 3, 4}
  - Remove duplicates = {3, 4}
   */
  set1 intersect set2

  /*
  8. Difference: set1 diff set2
  - set1 = {1, 2, 3, 4}, set2 = {3, 4, 5, 6}
  - combine both set = {1, 2, 3, 4, 3, 4, 5, 6}
  - Remove duplicates = {1, 2, 5, 6}
  - Remove elements of set2 = {1, 2}
   */
  set1 diff set2

  /*
  9. Check SubSet = set3 subsetof set1
  - set1 = {1, 2, 3, 4} set3 = {1, 2}
  - combine both set = {1, 2, 3, 4, 1, 2}
  - Remove duplicates = {3, 4}
  - Remove elements of set1 = {} -> if empty true else false
   */
  val set3 = Set(1, 2)
  set3 subsetOf set1
}
