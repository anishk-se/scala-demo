package com.anishk.ds.collections

object MapHOF extends App {

  /*
  1. Creation: Map()
  - What does -> mean? This is just syntactic sugar for creating a tuple.
  - "Alice" -> 25 ====== ("Alice", 25)
   */
  Map("Alice" -> 25, "Bob" -> 30, "John" -> 28)

  /*
  1. Creation: Map.empty
  - create an empty, and should define type with empty
   */
  // Since the map has no elements, Scala infer the types as Map[Nothing, Nothing].
  Map.empty
  Map.empty[String, Int]

  /*
  1. Creation: Map.from
  - Creates a Map from another collection which has key-value pairs.
  - You will get a compilation error if the source collection has not key-value pairs.
   */
  val data = List(("Alice", 25), ("Bob", 30), ("John", 28))
  Map.from(data)

  /*
  2. Access: def apply(key: K): V
  - Give me the value for this key.
  - What if the key doesn't exist? java.util.NoSuchElementException
   */
  val ages =  Map("Alice" -> 25, "Bob"   -> 30, "John"  -> 28)
  ages("Alice") // 25

  /*
  2. Access: def get(key: K): Option[V]
  - Option forces you to think about the "missing value" case instead of crashing.
   */
  ages.get("Alice") // Some(25)
  ages.get("Anish") // None

  /*
  2. Access: def getOrElse(key: K, default: => V): V
  - Get the value, or return this default if the key isn't found.
   */
  val config = Map("host" -> "localhost")
  config.getOrElse("host", "127.0.0.1") // localhost
  config.getOrElse("port", "8080") // 8080

  /*
  3. Inspection: def keys: Iterable[K]
  - Give me all the keys, it will return Iterable[TypeOfKeys].
   */
  val agesKey: Iterable[String] = ages.keys

  /*
  3. Inspection: def keySet: Set[K]
  - Give me all the keys as a Set, It will return Set[TypeOfKeys]
   */
  val agesKeySet: Set[String] = ages.keySet

  /*
  3. Inspection: def values: Iterable[V]
  - Give me all the values.
   */
  val agesValue: Iterable[Int] = ages.values

  /*
  4. Adding / Updating: def +(kv: (K, V)): Map[K, V]
  - Return a new map by adding/updating this key-value pair.
  - ("Anish", 30) will through compilation error, but Tuple2("Anish", 30) will work.
   */
  val person = Map("Alice" -> 25, "Bob" -> 30)
  person + ("Anish" -> 30)

  /*
  4. Adding / Updating: def ++(other: IterableOnce[(K, V)]): Map[K, V]
  - Return a new map containing all entries from both collections.
   */
  ages ++ person
  ages ++ List("John" -> 28, "Sam" -> 40)

  /*
  5. Removing: def -(key: K): Map[K, V]
  - Return a new Map without this key.
  - What if the key doesn't exist? No exception. Nothing happens.
   */
  val removingAges = Map("Alice" -> 25, "Bob" -> 30, "John" -> 28, "Sam" -> 40)
  removingAges - "Sam"
  removingAges - "Anish"

  /*
  5. Removing: def --(keys: IterableOnce[K]): Map[K, V]
  - Return a new map after removing all these keys.
   */
  removingAges -- List("Alice", "Bob")

  /*
  Pattern Matching: (Most Common) while working with Map element (key, value) that is tuple.
  - Instead of receiving a tuple, you can destructure it.
  - What does case (name, age) mean?
    - Remember, every element in Map is like ("Alice", 25) that is tuple.
    - Scala lets you unpack the tuple directly like: case (name, age)
   */
  val persons = Map("Alice" -> 25, "Bob" -> 30, "John" -> 28, "Sam" -> 40)
  // (Alice,25) (Bob,30) (John,28) (Sam,40)
  persons.foreach(ele => println(ele))

  persons.foreach {
    case (name, age) => println(s"$name is $age years old")
  }

  /*
   Other methods for Map:
   - Iteration, Transformation, Filtering, Searching, Aggregation
   - Grouping behave same, Just treat (key, value) as tuple.
   */

}
