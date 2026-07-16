<h2 align='center'>OBJECTS & CLASSES</h2>

### Classes & Constructors
```scala
class Person                           // Empty class
class Person(name: String, age: Int)  // Constructor params (not accessible)
class Person(name: String, val age: Int)  // val/var makes it a field (accessible)

class Person(name: String, age: Int) {
  println(s"$name, $age")  // Body executed on instantiation
}
```

### Case Classes
- Parameters automatically promoted to fields
- Automatic `toString`, `equals`, `hashCode`
- `copy()` method for creating modified copies
- Companion object with `apply()` method (no `new` needed)
- Can be used in pattern matching
- Serializable (great for distributed systems like Akka)

```scala
case class Person(name: String, age: Int)
val jim = Person("Jim", 30)    // No 'new' keyword needed
val jim2 = jim.copy(age = 40)  // Creates a copy with modified field
```

### Companion Objects
- Object sharing the same name as a class in the same file
- Can access private members of the companion class
- Often used with `apply()` for factory methods

```scala
class Person(val name: String, private val age: Int)
object Person {
  def apply(name: String, age: Int): Person = new Person(name, age)
  def printAge(p: Person): Unit = println(p.age)  // Can access private age
}
```

### Anonymous Classes
```scala
abstract class Animal { def eat(): Unit }
val funnyAnimal = new Animal {
  override def eat(): Unit = println("eating")
}
```

### Lazy Evaluation
- Delays evaluation until first use
- Evaluated only once despite multiple accesses

```scala
lazy val x: Int = { println("evaluating"); 42 }
println(x)  // prints: evaluating 42
println(x)  // prints: 42 (no re-evaluation)
```

### Exceptions
- `throw` expression has type `Nothing`
- Can be assigned to any variable
- Catch with pattern matching

```scala
// Define custom exception
class MyException extends Exception

// Try-catch-finally
try {
  stringToInt("invalid")
} catch {
  case e: RuntimeException => println(e.getMessage)
} finally {
  println("cleanup")  // Doesn't affect return value/type
}
```

---

<h2 align='center'>FUNCTIONS</h2>

### Lambda Functions (Anonymous Functions)
```scala
val doubler: Int => Int = (x: Int) => x * 2
val adder: (Int, Int) => Int = (x, y) => x + y
val noParams: () => String = () => "hello"

// Type inference
val betterDoubler: Int => Int = _ * 2      // Underscore syntax
val subtract: (Int, Int) => Int = _ - _
```

### Call by Value vs Call by Name
```scala
def callByValue(x: Long): Unit = println(x)      // Evaluates ONCE
def callByName(x: => Long): Unit = println(x)    // Evaluates EVERY use

callByValue(System.nanoTime())   // Same value twice
callByName(System.nanoTime())    // Different value each time
```

### Currying
- Function returning another function
- Can be written with multiple parameter lists

```scala
// Curried function
val adder: Int => Int => Int = x => y => x + y
val add3 = adder(3)
println(add3(10))  // 13

// Curried method
def curriedAdder(x: Int)(y: Int): Int = x + y
val add4: Int => Int = curriedAdder(4)

// ETA expansion (lifting method to function)
val add4ETA = curriedAdder(4)_  // Force with underscore
```

### Default Arguments & Named Arguments
```scala
def connect(host: String, port: Int = 8080, timeout: Int = 30): Unit = 
  println(s"$host:$port, timeout=$timeout")

connect("localhost")                    // Uses all defaults
connect("localhost", 9090)              // Sets port, timeout=30
connect("localhost", timeout = 60)      // Named arg skips port
```

### Partial Functions
- Function defined for only a subset of inputs
- Type: `PartialFunction[InputType, OutputType]`
- Use `isDefinedAt()` to check if defined for input
- Use `lift` to convert to `Option`-returning function
- Use `orElse` to chain partial functions

```scala
val partialFunc: PartialFunction[Int, String] = {
  case 1 => "one"
  case 2 => "two"
}

println(partialFunc.isDefinedAt(1))    // true
println(partialFunc.isDefinedAt(99))   // false
val lifted = partialFunc.lift          // Int => Option[String]

val extended = partialFunc orElse { case 3 => "three" }
```

---

<h2 align='center'>PATTERN MATCHING</h2>

### Basic Pattern Matching
```scala
val x = 2
val description = x match {
  case 1 => "The One"
  case 2 => "Double"
  case _ => "Something Else"  // Wildcard for default
}

// With guards
val greetings = person match {
  case Person(name, age) if age > 21 => s"$name can drink"
  case Person(name, age) => s"$name is $age"
  case _ => "Unknown"
}

// Pattern matching for case classes (destructuring)
case class Person(name: String, age: Int)
val Person(n, a) = Person("Bob", 22)  // Extract fields
```

---

<h2 align='center'>WRAPPERS FOR SAFE CODE</h2>

### Option (Handles absence of value)
- `Some(value)` - contains a value
- `None` - absence of value (singleton)

```scala
val opt: Option[Int] = Some(42)
val none: Option[Int] = None

// Creation
def mayReturn: Option[String] = if (condition) Some("value") else None
val result = Option(possiblyNull)  // Handles null automatically

// Operations
opt.isEmpty        // Check if None
opt.getOrElse(0)   // Safe retrieval with default
opt.orElse(fallback)

// Transformations (if Some, apply function; if None, return None)
opt.map(x => x * 2)         // Transform value
opt.flatMap(x => someFunc(x))  // If function returns Option
opt.filter(x => x > 10)     // Keep only if predicate true

// For-comprehension
val fullName = for {
  first <- firstName
  last <- lastName
  if first.nonEmpty
} yield s"$first $last"
```

### Try (Handles exceptions)
- `Success(value)` - operation succeeded
- `Failure(exception)` - operation failed

```scala
val result: Try[Int] = Try("100".toInt)   // Success(100)
val failed: Try[Int] = Try("abc".toInt)   // Failure(NumberFormatException)

// Checking status
result.isSuccess
result.isFailure

// Retrieval
result.getOrElse(0)
result orElse backupTry

// Transformations (Success applies function, Failure passes through)
result.map(x => s"$x converted")
result.flatMap(x => tryFunc(x))
result.filter(x => x > 50)   // If predicate fails, returns Failure
```

---

