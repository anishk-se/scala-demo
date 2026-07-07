# Scala Implicits – Interview Cheat Sheet

## 1. What are Implicits?
- Feature that lets Scala automatically supply values/conversions.
- Goal: Reduce boilerplate.

---

## 2. Types of Implicits
### Implicit Value
```scala
implicit val timeout: Int = 30
```

### Implicit Parameter
```scala
def greet(name: String)(implicit lang: String)
```

### Implicit Method
```scala
implicit def intToString(x: Int): String = x.toString
```

### Implicit Class
```scala
implicit class RichInt(x: Int) {
  def square = x * x
}
```

---

## 3. Implicit Scope
Spark/Scala searches in:
1. Local scope
2. Imported implicits
3. Companion objects

---

## 4. `implicitly[T]`
Returns the implicit value of type `T`.

```scala
val ec = implicitly[ExecutionContext]
```

---

## 5. Context Bound

```scala
def print[T: Ordering](a: T)
```

Equivalent to

```scala
def print[T](a: T)(implicit ord: Ordering[T])
```

---

## 6. Type Class Pattern

```scala
trait Serializer[T]
implicit object UserSerializer extends Serializer[User]
```

Benefits:
- Extensible
- Type-safe
- No inheritance required

---

## 7. Common Use Cases
- Ordering
- ExecutionContext
- JSON codecs
- Encoders
- Dependency injection

---

## 8. Interview Differences

### Implicit Value vs Parameter
- Value → provides dependency
- Parameter → consumes dependency

### Implicit Method vs Class
- Method → conversion
- Class → extension methods

### Context Bound vs Implicit Parameter
- Context Bound = shorter syntax
- Implicit Parameter = explicit access

---

## 9. Search Order
```
Local
 ↓
Imports
 ↓
Companion Objects
```

---

## 10. Best Practices
✔ Prefer implicit parameters over conversions
✔ Keep implicits close to their types
✔ Use companion objects
✔ Avoid multiple implicits of same type
✔ Prefer type classes

---

## 11. Avoid
✘ Too many implicit conversions
✘ Wildcard implicit imports
✘ Ambiguous implicits
✘ Hidden business logic

---

## 12. Frequently Asked Questions

**Q:** What is an implicit?
> A value/conversion supplied automatically by the compiler.

**Q:** What is implicit scope?
> Locations searched by the compiler for implicit values.

**Q:** What is `implicitly`?
> Retrieves the implicit value of a given type.

**Q:** Why type classes?
> Add behavior without modifying existing classes.

**Q:** Context Bound?
> Short syntax for an implicit parameter.

---

## 13. Revision Flow

```
Implicit
   │
   ├── Value
   ├── Parameter
   ├── Method
   └── Class
         │
         ▼
Implicit Scope
         │
         ▼
implicitly[T]
         │
         ▼
Context Bounds
         │
         ▼
Type Classes
         │
         ▼
Interview Questions
```

---

## 14. One-line Definitions

- Implicit: Compiler-supplied value/conversion.
- Implicit Value: Automatically available value.
- Implicit Parameter: Parameter filled by compiler.
- Implicit Method: Automatic conversion.
- Implicit Class: Adds extension methods.
- Implicit Scope: Places compiler searches.
- `implicitly[T]`: Fetch implicit value.
- Context Bound: Shorthand for implicit parameter.
- Type Class: Add behavior without inheritance.
