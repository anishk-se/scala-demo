package core.scala.learn.part1_objects

object SyntaxSugars extends App {

  // 1. method with one Arg (onlyyyyyyy oneeeee) can be called using {}, in place of ()
  def singleArgMethod(arg: Int): String = s"$arg little ducks"

  val description = singleArgMethod(10) // this we know, as usual
  val callWithCurly = singleArgMethod{ 10 } // we can call using {} as well

  // 2. Trait with single method can be reduced to lambda.
  trait Action {
    def act(x: Int): Int
  }

  val aAction = new Action {
    override def act(x: Int): Int = x + 1
  }

  // can be written as lambda, HERE TYPE IS MANDATORY,
  // otherwise it will be implementation of Function1[Int, Int]
  val aLambdaAction: Action = (x: Int) => x + 1

  // 3. update method: very much like apply()
  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // anArray.update(2, 7)

  // 4. setters and getters syntax sugar
  class MutablePerson {
    // define variable prefixed with _
    // so you will be able to define getter with actual variable name
    private var _age: Int = 0

    def age: Int = _age
    def age_=(value: Int): Unit = _age = value
  }
  val anish = new MutablePerson()
  anish.age = 20 // setter call anish.age_=(20)
  anish.age // getter call

}
