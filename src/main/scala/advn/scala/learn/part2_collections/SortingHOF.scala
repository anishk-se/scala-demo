package advn.scala.learn.part2_collections

// For sorting, we can use: sorted, sortBy, sortWith
// Sorting is Only applicable for List.
object SortingHOF extends App {
  /*
  1. sorted: def sorted(implicit ord: Ordering)
  - Used when elements already know how to compare themselves.
  - It sort the list by help of Ordering.
  - Ordering defines, How the elements will be arranged.
  - Scala provides predefined Ordering for built-in type, that arrange the elements in natural order.
   */
  val list  = List(10, 30, 20, 50, 40)
  println(list.sorted)

  /*
  2. sortBy: def sortBy(f: A => B)(implicit ord: Ordering)
  - Used most of the time because we usually sort Companions by one of their fields.
  - Just remember => sortBy(element => property)
  - sortBy sorts a collection based on a property (key) that you choose.
  - Ordering will be applied on this property.
   */
  println(list.sortBy(ele => ele))

  val names = List("Scala","Java","C","Python")
  // sort name by length
  println(names.sortBy(ele => ele.length))

  case class Student(name:String, marks:Int)
  val john = Student("John",90)
  val bob = Student("Bob",75)
  val alice = Student("Alice",82)

  val students = List(john, bob, alice)
  // sort by their marks
  println(students.sortBy(ele => ele.marks))

  /*
  3. sortWith: def sortWith(lt: (A, A) => Boolean)
  - simplified: sortWith((a, b) => comparison)
    - where a = first element and b = second element
    - Your function must return:
      - true → a should come before b (return true if you want order unchanged)
      - false → b should come before a (return false if you want order changed)
  - Use if you Need complete control over comparisons.
  - Tips to decide: treat first(a) and second(b) belongs to result, and write logic to return true.
   */
  // sort in descending order, it picks first = 30 and second = 10 => first(30) > first(10) => true
  println(List(30, 10, 50, 20).sortWith((first, second) => first > second))

  // sortest length String first
  val courses = List("Scala","Java","C","Python")
  // first("Scala").length = 5 and second("Java").length = 4
  // here we need to change the order, so return false
  println(courses.sortWith((first, second) => first. length < second.length))

  // Sort by marks descending (90 75) order not needed to change
  println(students.sortWith((first, second) => first.marks > second.marks))
}
