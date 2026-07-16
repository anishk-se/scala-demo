package core.scala.learn.part2_functions

// Currying: Turning a multi-arg function into a chain of single-arg part2_functions you can partially apply.
object ToCurryFunc extends App {

  def toCurry(f: (Int, Int) => Int): Int => (Int => Int) = {
    x => y => f(x, y)
  }
  /*
   Step 1: what goes in ---> f: (Int, Int) => Int
    - that means toCurry accepts two Int argument and return one Int as result
   Step 2: what goes out ---> Int => (Int => Int)
    - A function that takes one Int and return another function Int => Int
    - Int -----> Int => Int
      - Int -----> Int
   Step 3: The Body ---> x => y => f(x, y)
    - x => (y => f(x, y))
     - x ----> y => f(x, y)
      - y -----> f(x, y)
 */

  val add: (Int, Int) => Int = (a, b) => a + b

  // it means if you give add to toCurry ---> it decompose it in another function.
  val curriedAdd: Int => (Int => Int) = toCurry(add)

  // now curriedAdd decomposes the call into another function
  val step1: Int => Int = curriedAdd(3)
  // and finally step1 decomposes it into result
  val result: Int = step1(4)

  // Or written all in one line, since function call can be chained naturally.
  println(toCurry(add)(3)(4)) // 7

  /*
  Here's what happens under the hood:
   1. toCurry(add) — runs the body of toCurry, with f bound to add. Returns the lambda
      x => y => f(x, y) as an object.
   2. (3) — applies 3 to that object. This runs x => y => f(x, y) with x = 3, which returns the inner
      lambda y => f(3, y) (with x saved in scope, and this is called closure).
   3. (4) — applies 4 to that inner function, finally running f(3, 4) → add(3, 4) → 7.
   */


  // Why is this useful? Currying lets you fix one argument and reuse for the rest.
  val addFive: Int => Int = toCurry(add)(5)
  // addFive is a specialized function where x = 5 is saved, and you are just supplying y each time.
  println(addFive(10)) // 15
  println(addFive(20)) // 25

  /*
  Mental model to remember:
  f ----> (Int, Int) => Int --> you want to make f to be used like curry
  toCurry(f) ----> Int => (Int => Int) ----> it will take first param and returns.
  toCurry(f)(x) -----> Int => Int ---> it will take your second param.
  toCurry(f)(x)(y) -----> Int ---> it will call your function f with both param.
   */

  /*
  Elephant in the room: .curried
  -  Scala actually provides this as a built-in method, .curried, on function values.
   */
  add.curried // Int => Int => Int, same as toCurry(add)
}
