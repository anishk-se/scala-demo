<h1 align="center">Scala & Functional Programming Interview Practice</h1>

---
<p align="center"><img src="./src/main/resources/tail_rec.jpeg" alt="Tail Rec"></p>

---

<h2 align="center">Numbers</h2>
1. Reverse a number
2. Check palindrome number
3. Armstrong number
4. Prime number
5. Factorial
6. Fibonacci
7. Sum of digits
8. Count digits
9. GCD & LCM
10. Largest Number from a list

---
<h2 align="center">Strings</h2>
1. Reverse a string
2. Check palindrome
3. Check anagram
4. Character frequency
5. First non-repeating character
6. Remove duplicate characters
7. Reverse words in a sentence
8. Valid Parenthesis
9. String compression
10. Longest common prefix

---
<h2 align="center">Maps</h2>
1. Word frequency
2. Character frequency using Map
3. Count occurrences of elements
4. Find duplicate elements
5. First non-repeating element
6. Two Sum using Map
7. Merge two maps
8. Most frequent element
9. Group strings by first character
10. Group anagrams

---
<h2 align="center">Collection</h2>
<p align="center"><img src="./src/main/resources/colletion_diagram.png" alt="Tail Rec"></p> 

- Colored Box is default implementation
- Seq is indexed, Set is unique and Map is key-value. (Tree* - is sorted)
- Everything above is Immutable, Scala also has mutable collections. (scala.collection.mutable._)
- Scala collections are designed for transformation, Instead of changing the original collection, you create a new one.

### Common method for all collection, because it is defined in Iterable

| Category  | Methods                                     |
|-----------|---------------------------------------------|
| Iterate   | `foreach`                                   |
| Transform | `map`, `flatMap`, `collect`                 |
| Filter    | `filter`, `filterNot`, `partition`          |
| Search    | `find`, `exists`, `forall`                  |
| Aggregate | `fold`,`foldRight`, `reduce`, `reduceRight` |
| Group     | `groupBy`, `groupMap`                       |
| Info      | `size`, `isEmpty`, `nonEmpty`, `min`, `max` |

---
<h2 align="center">List</h2>
<h3 align="center">A List is an ordered, linear sequence implemented as a singly linked list.</h3>

| Category     | Important Methods                       |
|--------------|-----------------------------------------|
| **Creation** | `List()`, `Nil`, `from`                 |
| **Adding**   | `::`, `+:`, `:+`, `patch`               |
| **Updating** | `updated`                               |
| **Reading**  | `apply`, `lift`, `head`, `last`, `tail` |
| **Deleting** | `drop`                                  |

#### List specific operation

| Category        | Important Methods                          |
|-----------------|--------------------------------------------|
| **Slicing**     | `take`, `drop`, `slice`, `splitAt`, `span` |
| **Combination** | `zip`, `unzip`, `zipwithIndex`             |

---

<h2 align="center">Set</h2>
<h3 align="center">A Set is a collection of unique elements that is optimized for fast membership testing.</h3>
<h4 align="center">Duplicate elements will be updated, so no duplicate in set</h4>

| Category     | Important Methods       |
|--------------|-------------------------|
| **Creation** | `List()`, `Nil`, `from` |
| **Adding**   | `+`, `++`               |
| **Updating** | No Method               |
| **Reading**  | `apply`, `contains`     |
| **Deleting** | `-`, `--`               |

#### Set specific operation

| Category         | Important Methods |
|------------------|-------------------|
| **Union**        | `union`           |
| **Intersection** | `intersect`       |
| **Difference**   | `diff`            |
| **SubSet**       | `subsetof`        |

---
<h2 align="center">Map</h2>
<h3 align="center">A Map is a collection of key-value pairs, keys in a map are always unique.</h3>

| Category       | Important Methods             |
|----------------|-------------------------------|
| **Creation**   | `Map()`, `empty`, `from`      |
| **Adding**     | `+`, `++`                     |
| **Updating**   | `+`, `++`                     |
| **Reading**    | `apply`, `get`, `getOrElse`   |
| **Deleting**   | `-`, `--`                     |

#### Map specific operation

| Category                | Important Methods           |
|-------------------------|-----------------------------|
| **Inspection**          | `keys`, `keySet`, `values`  |

#### behave same like Iterable, Just treat tuple as single element and extract using case (key, value) ⇒ 
**Iteration**  `foreach`, **Transformation**  `map`, `flatMap`, `collect`
**Filtering** `filter`, `filterNot` **Searching** `find`, `exists`, `forall`,`contains`
**Aggregation** `foldLeft`, `reduceLeft`  **Grouping** `groupBy`, `groupMap`
**Info** `size`, `isEmpty`, `nonEmpty`



