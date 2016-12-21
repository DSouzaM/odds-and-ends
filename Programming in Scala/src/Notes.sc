/*
---
Chapter 1: A Scalable Language

About Scala:
- compiles to JVM bytecode and runs on the JVM
- uses many Java libraries and can call Java methods, inherit Java classes, and implement Java interfaces
- has both OO and functional aspects
- statically-typed
- was built to be concise, high level, and scalable
*/


/*
---
Chapter 2: First Steps in Scala

The Scala interpreter is a shell for scripting with Scala.

Variables can be immutable "val"s or "var"s. They can be initialized with types, or they can be left out and inferred:
*/
val x: Int = 5
val y = 5 // type Int inferred
var z = 5 // mutable variable
/*
Functions start with "def", take typed parameters, and specify a result type. The parameter types must be provided, but the result type can be inferred.
*/
def add(a: Int, b:Int) : Int = {
  a + b // the last line of a scala function is returned, so "return" is not needed here
}
def add2(a: Int, b:Int) = a + b // curly braces omitted if result is one line
/*
A result type of "Unit" is similar to "void" in Java.
Functions with no parameters can be invoked without parentheses. If a function is used for its side effects, it is good practice to include the parentheses.
 */
def f() = 42
def g() = println(42)
val num = f
g()
/*
If statements and while loops behave as expected.
Scala does not have an iterative for loop to encourage using functional paradigms. It has a foreach method which can be applied to sequential data.
For-each loops as in Java exist as well.
 */
List(1,2,3,4).foreach(x => println(x)) // functions are first-class and can be passed as arguments
for(x <- List(1,2,3,4)) {
  println(x)
}


/*
---
Chapter 3: Next Steps in Scala

Scala was designed to encourage the use of functional paradigms.
Accessing elements of sequences is done using parentheses instead of square brackets.
Types for generic classes can be specified using square brackets.
Writing parentheses following any type invokes the "apply" function of the method.
 */
val list = List[Float](1,2,3,4)
2.0 == list(1) && list(1) == list.apply(1)
/*
Operators (+, -, *, /, etc.) are methods of classes rather than reserved tokens.
Writing "a op b" is equivalent to invoking the "op" method of a with b as a parameter.
 */
1 + 2 == 1.+(2)
(1 to 5) == 1.to(5)
/*
Writing functions which don't have side effects is a core idea of functional programming. Doing so makes code more reliable and maintainable.
Declaring a variable as a "val" means it cannot be assigned to another object, but the object itself can still be mutable.
Lists are immutable sequences which encourage functional style.
 */
val nums = Array(1,2,3)
nums(2) = 4
val nums2 = List(1,2,3)
//nums2(2) = 4 (will not compile)
/*
There are mutable and immutable versions of most collection classes (sets, maps, etc.).
Map entries are key-value pairs, joined by the "->" function (which converts the key and value to an appropriately-typed 2-tuple).
 */
val mutableSet : scala.collection.mutable.Set[String] = new scala.collection.mutable.HashSet[String]
mutableSet += "new"
mutableSet += ("entries", "can", "be", "added")
val mutableMap : scala.collection.mutable.Map[String, Int] = new scala.collection.mutable.HashMap[String, Int]
mutableMap += ("Woofles" -> 3)
mutableMap("Woofles") == 3
/*
Classes in Scala define constructor parameters in the class signature, and automatically use the arguments as field values of the class.
Any code not inside the body of a method in a class is executed when an object is constructed.
The constructor defined by the signature is the "primary" constructor.
Multiple "auxiliary" constructors can be defined as methods named "this", and the first statement in them should be a call to another constructor.
 */
class Animal(species:String, val cute:Boolean) { // writing "val" before the second parameter makes it publicly accessible.
  if (species == "dog") println("Good choice.")
  def this(species:String) = this(species, true)
  def speak() = {
    println(s"Hi! I am a $species. I am ${if (!cute) "" else "not "}cute.")
  }
}
val dog = new Animal("dog") // > Good choice.
val cat = new Animal("cat", false)
dog.speak() // > Hi! I am a dog. I am cute.
cat.speak() // > Hi! I am a cat. I am not cute.
/*
Scala classes cannot have static fields. Instead, one can create a singleton object.
Singleton objects need not be instantiated - they are automatically instantiated the first time they are used.
A singleton object can share the same name as a class. When this occurs, it is referred to as a companion object.
 */
object Animal {
  def describe(a: Animal) = if (a.cute) "cute" else "not cute"
}
Animal.describe(dog) == "cute"
/*
Command-line compilation and execution are similar to Java.
Use "scalac <files>" to compile, and "scala <file>" to run.
The "fsc" command can be used instead of "scalac" to create a background process to host the compiler rather than start up the Java runtime every compilation.
The entry point of Scala programs is a "main" method with an array of Strings as parameter within a standalone object. Alternatively, an object can extend the "App" trait.

Scala traits are similar to Java interfaces. The methods defined in a trait can abstract (as in Java), or explicitly defined.
A class can extend one other class, and extend many traits.
When overriding a method of a superclass, the keyword "override" is used.
 */
trait Shape {
  def draw():String = "pretend I know what this shape looks like"
}
class Square extends Shape {
  override def draw():String = "[]"
}
class WeirdSquare extends Square {
  override def draw():String = "<>"
}
val sq: Shape = new Square
val wsq: Shape = new WeirdSquare
sq.draw == "[]"
wsq.draw == "<>" // dynamic dispatch
/*
It is possible to mix traits in at instantiation. When this happens, the compiler creates a "synthetic" class and instantiates it.
 */
trait SquigglyShape extends Shape {
  override def draw():String = "~" + super.draw() + "~"
}
val squigglySquare : Shape = new Square with SquigglyShape
squigglySquare.draw() == "~[]~"

/*
---
Chapter 4: Classes and Objects

In Scala, initialization must occur during declaration.
In contrast to Java, where primitive types (int, float, etc.) are not objects, every value is an object in Scala.
The underlying Java implementation for primitives in Scala is ambiguous, i.e. a Scala Int may be a Java primitive int or an Integer.

Class fields are public by default; the private keyword makes them private.
Scaladoc, which is more or less the same as Javadoc, can be used for documentation.
Variable scope is standard, but Scala allows for variable shadowing in inner scopes.
Semicolon inference allows you to omit semicolons between expressions.
Line endings are treated as semicolons unless the line ending cannot be the ending of a statement, the next line cannot be the start of a statement, or the line ends inside nested parentheses or brackets.

Singleton objects can also be used as the entry point to a Scala application by defining a main method with an argument of type Array[String] and a result type of Unit.
 */
object Example {
  def main(args: Array[String]): Unit = {
    args.foreach(println)
  }
}

/*
---
Chapter 5: Basic Types and Operations

Scala's basic types have the same ranges as Java primitives.
Rules about literals:
  - 0x__ is a hex number
  - 0___ is an octal number
  - otherwise, a number is decimal
  - an integer literal ending in l is a Long
  - a decimal literal ending in f is a Float
  - character literals are quotes with single quotes
  - octal characters can be specified with '\#', hex characters can be specified with '\u#'

All operators are methods of the operands' types.
Infix operators are just method calls with the parameters. Prefix operators are limited to unary +, -, !, and ~.
 */
1 + 2l == 1.+(2l)
val d : String = "Doggies"
d.indexOf('g') == (d indexOf 'g')
d.indexOf('g',3) == (d indexOf ('g', 3))
-2 == 2.unary_-
d.toLowerCase == (d toLowerCase)
/*
The == operator calls the equals() method as long as neither side is null.
Whereas Java's == checks reference equality for objects, in Scala it compares object equality. The eq and ne operators can be used to check reference equality.

Bitwise operators &, |, ^, and ~ perform AND, OR, XOR, and complementing on bits. << and >> can shift left and right while maintaining sign, while >>> shifts right and fills with zeroes.

To decide operator precedence, Scala prioritizes methods based on the first character in their names (e.g. *** > +++).
Operators are left-associative (grouped left to right).
The exception to this is when the operator ends in ":", in which case the operator is right-associative since it is a method call of the right operand with the left operand as argument.
 */
5 + 2 * 3 == 5 + (2 * 3)
1 * 2 * 3 == (1 * 2) * 3
1 * 2 * 3 == 1.*(2).*(3)
List(1) :: List(2) :: List(3) == List(1) :: (List(2) :: List(3))
List(1) :: List(2) == List(2).::(List(1))
/*
The basic types have rich wrappers which provide additional functionality.
 */
(0 max 5) == 5
(-2.7 abs) == 2.7
((1.0/0) isInfinity) == true
("bob" capitalize) == "Bob"

/*
---
Chapter 6: Functional Objects

vals should generally be preferred over vars because they are easier to reason about. Nonetheless, there are still circumstances where it makes more sense to use vars.
The primary constructor of a class takes the class parameters and executes all statements in the class body.
Auxiliary constructors can be defined as methods named "this" with different parameters. They must call another constructor as their first statement, so that the primary constructor is the point of entry for a class.

Overriding the toString method defines the way to print an object of a class.
Private fields can only be accessed from within a class; protected fields can be accessed within a class or within a subclass.
The "this" keyword can be used to refer to an object or its members, but "this.foo" is equivalent to just "foo" so it's not usually necessary.

In addition to regular alphanumeric identifiers, Scala supports:
  - operator identifiers (+, :, ?, ~, #, etc.) are ASCII characters which are not letters, digits, or reserved symbols
  - mixed identifiers (vector_+, success_?, etc.) are alphanumeric identifiers followed by an underscore and an operator identifier
  - literal identifiers (`x`, `yield`, etc.) are strings wrapped in back-ticks, which can be useful to avoid collision with reserved keywords

Methods can be overloaded with different parameters. Overloading resolution is done to best match the arguments' static types as in Java.
 */
class Rational(n: Int, d: Int) {
  def this(n: Int) = this(n,1) // create rationals without specifying denominator

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(n, d) // only accessible from within this class
  val numer: Int = n / g
  val denom: Int = d / g

  def +(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
  def +(that: Int): Rational = this + new Rational(that)
  def -(that: Rational): Rational = new Rational(numer * that.denom - that.numer * denom, denom * that.denom)
  def -(that: Int): Rational = this - new Rational(that)
  def *(that: Rational): Rational = new Rational(numer * that.numer, denom * that.denom)
  def *(that: Int): Rational = this * new Rational(that)
  def /(that: Rational): Rational = new Rational(numer * that.denom, denom * that.numer)
  def /(that: Int): Rational = this / new Rational(that)
  def ==(that: Rational): Boolean = numer == that.numer && denom == that.denom
  def ==(that: Int): Boolean = this == new Rational(that)
  override def toString: String = numer + "/" + denom
}
val r1 = new Rational(2,3)
r1.toString == "2/3"
val r2 = new Rational(3,9)
r2.toString == "1/3"
r1 + r2 == new Rational(1)
r1 + 1 == new Rational(5,3)
r1 * 3 + r2 * 3 == new Rational(3) // operator precedence holds

/*
Implicit conversions can be used to convert one type to another. Any time the type is used when the other is expected, the function will be called with the value.
While useful, implicit conversions should be used sparingly.
 */
implicit def intToRational(x: Int): Rational = new Rational(x)
r1 * 3 == 2 // this worked before
3 * r1 ==  2 // this works because of the implicit def

/*
---
Chapter 7: Built-in Control Structures

Scala has minimal control structures, most of which return a value.
If statements can be used in a ternary fashion to return a value. If statements without an else branch cannot have the returned type, since they do not always return a value of that type.
 */
val ifElseVal = if (true) "true" else "false" // type is String
val ifElseVal2 = if (true) true else "false" // type is Any
val ifVal = if (false) "false" // type is Any
/*
While loops and do-while loops are useful for iteration.
While loops follow a more imperative style, but sometimes provide more readable solutions.
 */
var count = 3
while(count > 0) {
  print(count + " ")
  count -= 1
}
count = 0
do {
  print(count + " ")
  count -= 1
} while (count > 0)
println()
/*
For expressions enable many different kinds of enumeration:
  - iteration over entire collections
  - iteration over collections with a filter
  - nested iteration
  - creating a new collection
 */
val numList = List(1,2,3,4,5,6)
for (x <- numList) println(x); // prints 1 through 6
for (x <- numList; if x % 2 == 0) println(x); // prints 2, 4, and 6
for (x <- numList; if x % 2 == 0; if x != 4) println(x); // prints 2 and 6
for {
  x <- numList
  if x % 2 == 0
  if x != 4
} println(x) // curly braces can be used in place of parentheses, in this case to avoid use of semicolons

val listList = List(List(1,2,3),List(4,5), List(6,7,8))
for {
  list <- listList
  if list.length > 2
  num <- list
  if num % 2 == 1
} println(num) // prints 1, 3, and 7
for {
  list <- listList
  if list.length > 2
  num <- list
  if num % 2 == 1
} yield num // returns a list containing 1, 3, and 7. The collection type is based on the collection iterated over.
/*
Try expressions call code which may throw exceptions.
The try block is a set of statements to execute, a catch block catches any thrown exceptions, and a finally block is code to always execute even if an exception is thrown.
The catch block uses pattern matching to identify specific exception types. They can also yield values.
It is not required, like in Java, to indicate that a method throws an exception.
 */
val exceptionResult =
  try {
    println("executing risky code")
    throw new NullPointerException
    "not an NPE"
  } catch {
    case ex : NullPointerException => "NPE" // this becomes the result
  } finally {
    println("call complete, exceptions handled")
  }
/*
An explicit return statement in the finally block will override any other return value.
 */
def finally1(): Int = try { 1 } finally { 2 }
def finally2(): Int = try { 1 } finally { return 2 }
finally1() == 1
finally2() == 2
/*
Match expressions allow for pattern matching based on types and values. They are similar to switch statements, but more powerful.
 */
def matchFn(x: Any): String = x match {
  case x:Int if x > 100 => "It's a large Int"
  case x:Int => "It's an Int"
  case x:String => "It's a String"
  case _ => "It's something else" // default case
}
matchFn(500) // "It's a large Int"
matchFn(5) // "It's an Int"
matchFn("dogs") // "It's a String"
matchFn(Nil) // "It's something else"
