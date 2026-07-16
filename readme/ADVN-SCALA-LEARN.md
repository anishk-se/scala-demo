<h2 align='center'>GENERICS</h2>

### Basic Generics
```scala
class MyClass[T](ele1: T, ele2: T)
trait MyTrait[T]
def MyFunction[T](value: T): T

// Type inference for functions
def print[T](value: T): Unit = println(value)
print(10)  // Compiler infers T = Int
```

### Type Variance

#### Invariance (No inheritance support)
```scala
class DogShop[T]
val dogCage: DogShop[Dog] = new DogShop[Dog]
// Cannot assign DogShop[Dog] to DogShop[Animal]
```

#### Covariance (+T - supports inheritance)
```scala
class Cage[+T]  // + means covariant
val dogCage: Cage[Animal] = new Cage[Dog]  // OK! Dog IS-A Animal

// With upper bound
class BetterCage[+T <: Animal]  // T must be Animal or subtype
val betterDogCage: BetterCage[Animal] = new BetterCage[Dog]  // OK
// val stringCage: BetterCage[String] = new BetterCage[String]  // Compilation error!
```

#### Contravariance (-T - opposite of inheritance)
```scala
class Trainer[-T]  // - means contravariant
val dogTrainer: Trainer[Dog] = new Trainer[Animal]  // OK! Animal is supertype of Dog
```

### Type Bounds
```scala
class BetterCage[+T <: Animal]  // Upper Bound: T must be Animal or subtype
def compare[T >: Dog](a: T, b: T): Boolean  // Lower Bound: T must be Dog or supertype
```

---

<h2 align='center'>COLLECTIONS</h2>

### Lists - Creating & Accessing
```scala
val list = List(1, 2, 3)
val empty = List.empty[Int]
List.from(list)

// Adding elements
0 :: list              // Prepend (head)
0 +: list              // Prepend
list :+ 4              // Append
list.patch(3, List(4), 0)  // Insert at position

// Reading
list.head              // 1
list.headOption        // Some(1)
list.tail              // List(2, 3)
list.last              // 3
list.lastOption        // Some(3)
list(2)                // 3 (can throw IndexOutOfBoundsException)
list.lift(5)           // None (safe access)

// Deleting/Slicing
list.drop(2)           // Skip first 2 elements
list.take(2)           // Take first 2 elements
list.slice(1, 4)       // From index 1 to 4
list.splitAt(2)        // Split at position: (List(...), List(...))
list.span(_ < 3)       // Take while predicate true: (List(...), List(...))

// Combination
val l1 = List(1, 2, 3)
val l2 = List("a", "b")
l1.zip(l2)             // List((1,a), (2,b), (3,c))
list.zipWithIndex      // List((1,0), (2,1), (3,2))
```

### Maps - Creating & Accessing
```scala
val ages = Map("Alice" -> 25, "Bob" -> 30)  // -> is syntax sugar for Tuple2
Map.empty[String, Int]
Map.from(List(("Alice", 25), ("Bob", 30)))

// Access
ages("Alice")          // 25 (throws NoSuchElementException if not found)
ages.get("Alice")      // Some(25)
ages.get("Unknown")    // None
ages.getOrElse("port", "8080")  // 8080 (safe default)

// Inspection
ages.keys              // Iterable[String]
ages.keySet            // Set[String]
ages.values            // Iterable[Int]

// Adding/Updating
ages + ("Anish" -> 30)
ages ++ List("John" -> 28, "Sam" -> 40)

// Removing
ages - "Sam"           // Remove one key (no exception if missing)
ages -- List("Alice", "Bob")  // Remove multiple keys

// Pattern matching (destructure tuples)
ages.foreach { case (name, age) => println(s"$name is $age") }
```

### Sets - Operations
```scala
val fruits = Set("Apple", "Banana", "Orange")
Set.empty[Int]
Set.from(List(1, 2, 3))

// Adding
fruits + "Grapes"
fruits ++ List("Guava", "Grapes")

// Reading (checking existence)
fruits("Apple")        // true
fruits.contains("Apple")  // true

// Deleting
fruits - "Apple"       // Remove one
fruits -- List("Apple", "Banana")  // Remove multiple

// Set Operations
val set1 = Set(1, 2, 3, 4)
val set2 = Set(3, 4, 5, 6)
set1 union set2        // Set(1, 2, 3, 4, 5, 6)
set1 intersect set2    // Set(3, 4)
set1 diff set2         // Set(1, 2)
set1 subsetOf set1     // true
```

### Tuples
```scala
val tuple = (2, "Hello")
tuple._1               // 2
tuple._2               // "Hello"
tuple.copy(_2 = "Goodbye")  // (2, Goodbye)
tuple.swap             // ("Hello", 2)
```

### Sorting
```scala
val list = List(10, 30, 20, 50, 40)

// sorted - natural order (requires implicit Ordering)
list.sorted            // List(10, 20, 30, 40, 50)

// sortBy - sort by property/key
val names = List("Scala", "Java", "C", "Python")
names.sortBy(_.length) // Sorted by string length
students.sortBy(_.marks)  // Sort by field

// sortWith - custom comparison
List(30, 10, 50, 20).sortWith((a, b) => a > b)  // Descending
names.sortWith((a, b) => a.length < b.length)   // By length ascending
// Logic: return true = keep order, false = swap
```

---

<h2 align='center'>FUTURES</h2>

### Creating & Waiting
```scala
val myFuture: Future[String] = Future {
  "code runs on separate thread"
}  // Requires implicit ExecutionContext

// Get value (Option[Try[A]])
myFuture.value         // None (not completed), Some(Success(...)), Some(Failure(...))

// Wait for completion with callback
Future(BankDatabase.getCustomer("Anish")).onComplete {
  case Success(cust) => println(s"Success: ${cust.name}")
  case Failure(ex) => println(s"Failed: ${ex.getMessage}")
}

// Blocking wait with timeout
Await.result(future, 2.seconds)  // Blocks main thread, throws TimeoutException
```

### Transformations (map, flatMap, filter)
```scala
// map - Success applies function, Failure passes through
Future("100".toInt).map(x => s"$x converted")

// flatMap - when function returns Future
Future(getCustomer).flatMap(cust => getAccount(cust.id))

// filter - if predicate fails, returns Failure
Future(100).filter(_ > 50)  // Success
Future(100).filter(_ > 200)  // Failure(NoSuchElementException)

// for-comprehension (chains flatMap/map)
val result: Future[String] = for {
  cust <- Future(getCustomer("Anish"))
  acc <- Future(getAccount(cust.id))
} yield s"balance: ${acc.balance}"
```

### Error Handling
```scala
// recover - maps Failure to Success (like catch)
Future(6 / 0) recover { case e: ArithmeticException => 0 }

// recoverWith - maps Failure to another Future
Future(6 / 0) recoverWith { case e: ArithmeticException => Future { Int.MaxValue } }

// orElse - fallback Future if first fails
future1 orElse future2
```

### Helper Methods
```scala
// Immediate success (no new thread)
Future.successful("value")

// Run in sequence (first completes, then second)
def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] = 
  first.flatMap(_ => second)

// Convert List[Future[A]] to Future[List[A]]
val listOfFutures: List[Future[String]] = List(...).map(e => upperCase(e))
Future.sequence(listOfFutures)  // Future[List[String]]

// Transform each element
Future.traverse(listOfFutures)(ele => ele.map(_.length))
```

### Promise (Manual Future completion)
```scala
// Promise = box to manually complete a Future
val promise = Promise[DeliveryStatus]()
val future = promise.future

// Manually complete
promise.success(Delivered)
promise.failure(new Exception("Failed"))
// After completion, no more changes allowed

// Use case: Chat server waiting for receiver to read message
val promise = Promise[DeliveryStatus]()
pendingPromises += (messageId -> promise)  // Store for later
promise.future  // Return to sender

// When receiver reads
promise.success(Delivered)  // Complete the promise
```

---

<h2 align='center'>IMPLICITS</h2>

### Types of Implicits

#### Implicit Value
```scala
implicit val timeout: Int = 30
implicit val executionContext: ExecutionContext = ExecutionContext.global
```

#### Implicit Parameter
```scala
def increment(number: Int)(implicit inc: Int): Int = number + inc
implicit val defaultInc: Int = 1
increment(10)      // Compiler finds and passes implicit value: 11
increment(10)(10)  // Can override: 20
```

#### Implicit Method (Conversion)
```scala
implicit def fromStringToPerson(str: String): Person = Person(str)
"Anish".greet  // Compiler converts: fromStringToPerson("Anish").greet
```

#### Implicit Class (Extension Methods / Pimp My Library)
```scala
implicit class RichInt(value: Int) {
  def isEven: Boolean = value % 2 == 0
  def isGreaterThan(number: Int): Boolean = value > number
}

10.isEven           // new RichInt(10).isEven
10.isGreaterThan(5) // new RichInt(10).isGreaterThan(5)
```

### Context Bound (Shorthand Syntax)
```scala
// Explicit implicit parameter
def print[T](a: T)(implicit ord: Ordering[T]): Unit = println(ord.compare(a, a))

// Context bound (shorthand)
def print[T : Ordering](a: T): Unit = println(a)

// To access the implicit inside context bound
def printBetter[T : Ordering](a: T): Unit = {
  val ord = implicitly[Ordering[T]]
  println(ord.compare(a, a))
}
```

### implicitly[T]
```scala
// Fetch implicit value of type T from current scope
case class Permission(mask: String)
implicit val defaultPermission: Permission = Permission("0744")

val standardPermission = implicitly[Permission]  // Retrieves implicit value
```

### Implicit Scope (Where compiler searches)
```
Priority Order:
1. Local scope (where code is written)
2. Imported scope (imported implicits)
3. Companion objects (companion of types in method signature)

// Example: List[Int].sorted
// Compiler searches for Ordering[Int] in:
// - Local scope
// - Imported implicits
// - Companion of List, Ordering, Int
```

### Organizing Implicits
```scala
// Search for implicit based on type
case class Person(name: String, age: Int)

implicit val orderByName: Ordering[Person] = 
  Ordering.fromLessThan[Person]((p1, p2) => p1.name < p2.name)

List(steve, amy, john).sorted  // Sorts by name

// If multiple implicits of same type exist → Compilation error (ambiguous)
```

### Type Class Pattern
```scala
// Define type class
trait HTMLSerializer[T] {
  def toHtml(value: T): String
}

// Implement for specific types
implicit object StringSerializer extends HTMLSerializer[String] {
  def toHtml(value: String): String = s"<p>$value</p>"
}

implicit object IntSerializer extends HTMLSerializer[Int] {
  def toHtml(value: Int): String = s"<h1>$value</h1>"
}

// Use with implicit parameter
def render[T](content: T)(implicit serializer: HTMLSerializer[T]): String = {
  serializer.toHtml(content)
}
```

---

<h2 align='center'>COMMON PATTERNS</h2>

### Transform List of Futures
```scala
val listOfFutures: List[Future[String]] = list.map(item => asyncFunc(item))
Future.sequence(listOfFutures)  // Future[List[String]]
```

### Chain Multiple Futures
```scala
for {
  user <- fetchUser(id)
  account <- fetchAccount(user.accountId)
  transactions <- fetchTransactions(account.id)
} yield (user, account, transactions)
```

### Extend Built-in Type
```scala
implicit class RichString(s: String) {
  def isPalindrome: Boolean = s == s.reverse
}
"radar".isPalindrome  // true
```

### Custom Ordering
```scala
implicit val personOrdering: Ordering[Person] = 
  Ordering.by[Person, String](_.name)
List(people).sorted  // Sorted by name
```

