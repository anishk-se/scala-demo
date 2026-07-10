package scala.udemy.learn.objects


object Classes extends App {

  class Person
  /*
   - class has --- body {}, parameter ()
   - {} has --- fields, behaviour, super, this
   - behaviour defined inside calls is called methods.
  */
  val person = new Person

  /*
   (name: String, age: Int)
   - It is a constructor, it says every single instance must be created
   - by passing the name and age.
  */
  class PersonWithCons(name: String, age: Int)
  /*
   println(personWithCons.age)
   - age is a constructor parameter, not a class field/member
   - age or name can not be accessed with .
  */
  val personWithCons = new PersonWithCons("Anish", 30)

  class PersonWithField(name: String, val age: Int)
  /*
   println(personWithField.age)
   - this is valid
   - By adding val or var, we can convert constructor param to class field.
  */
  val personWithField = new PersonWithField("Anish", 30)

  class PersonWithBody(name: String, age: Int) {
    println(s"Hi, I am $name and $age years old.")
  }
  /*
   {} : is body of class
   - At every instantiation of class, body will execute
   - so it will print "Hi, I am Anish and 30 years old."
   */
  val personWithBody = new PersonWithBody("Anish", 30)

}
