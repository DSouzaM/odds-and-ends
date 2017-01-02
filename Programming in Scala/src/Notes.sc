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
A class can extend one other class, and have many traits.
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


/*
---
Chapter 8: Functions and Closures

Scala offers more in the way of functions than Java, such as nested functions, function literals, and using functions as values.

Methods are the traditional approach to functions. They exist as members of a class.
*/
object methodApproach {
  var multiplier = 2
  def mainMethod(): Int = {
    helperMethod(21)
  }
  def helperMethod(x: Int): Int = x*multiplier
}
/*
Nested functions allow helper functions to be encapsulated within the function that uses them, which avoids namespace collisions and clutter.
 */
def nestedMainFunction(): Int = {
  val multiplier = 2
  def helperFunction(x: Int): Int = x*multiplier // anything within a parent block is in scope in a nested function
  helperFunction(21)
}
/*
First-class functions allow functions to be treated as values (i.e. as variables or parameters to other functions).
First-class functions can be written as function literals.
Parameter types can be inferred by the compiler when they are obvious (e.g. when a literal is being passed as a function of type (Int) => Int, its parameter type is inferred to be Int).
 */
def invoke(func: (Int)=>Int, x: Int): Int = func(x)
invoke((x) => x*x, 8) == 64 // the parameter type of the function is inferred
val multiplyByTwo = (x: Int) => x*2
invoke(multiplyByTwo, 21) == 42
/*
Placeholder syntax uses underscores as placeholders for parameters, and can allow for more concise code. Sometimes the types of the placeholders need to be specified.
 */
List(1,2,3,4,5,6).filter(_ % 2 == 0) == List(2,4,6)
val checkDouble = (_: Int) * 2 == (_: Int)
checkDouble(2,4)
/*
Partially applied functions create function values with some or none of the parameters of the original function provided.
 */
def sum(a: Int, b:Int, c:Int) = a + b + c
val partial1 = sum _
partial1(1,2,3) == 6
val partial2 = sum(1, _ : Int ,3)
partial2(2) == 6 // this function call invokes sum(1, 2, 3)
/*
Free variables are unbound, unspecified variables.
Closures create function values which use the environment at the point of closure creation to "close" over any free variables.
 */
var multiplier = 3
val multiplyClosure = (x: Int) => x * multiplier
multiplyClosure(14) == 42
multiplier = 6
multiplyClosure(7) == 42
/*
Repeated parameters allow functions to take variable length argument lists, by appending an asterisk to the parameter.
 */
def nameList(names: String*) = names.foreach(println)
nameList("Michael")
nameList("Alice", "Bob")
nameList() // nothing
val nameArr = Array("Jake", "Duke", "Daisy")
nameList(nameArr: _*) // use arrays as repeated parameters by explicitly typing them as repeated
/*
Tail recursive functions call themselves as the last operation of the function.
The compiler optimizes tail recursive functions as if they just jumped back to the beginning of the function body, rather than overflowing the stack.
 */


/*
---
Chapter 9: Control Abstraction

Higher order functions can help reduce code duplication. When you have many similar functions, sometimes writing one function which takes a function value as a parameter can reduce code clutter.
 */
val stringList = List("Hulk", "Dr. Strange", "Tony Stark")
def matchStrings(matcher: (String) => Boolean) = {
  // extra code to open file, query DB, etc.
  stringList.filter(matcher)
}
def matchEnds(end: String) = matchStrings(_.endsWith(end)) // equivalent to (str: String) => str.endsWith(end)
def matchContains(substr: String) = matchStrings(_.contains(substr))
def matchRegexs(regex: String) = matchStrings(_.matches(regex))
/*
Scala's API supports helps reduce code duplication, especially with the use of higher-order functions.
The collections classes, for example, support an "exists" method which handles iteration over elements and testing each of them against some function. Since the iteration is handled, the client must simply provide a function as parameter.
 */
List(1,2,3,4,5,6).exists((x:Int) => x * 2 == 4)
!List("This", "is", "a", "list", "of", "strings").exists(_.length == 5)
/*
Curried functions have multiple argument lists.
 */
def uncurriedAdd(a: Int, b: Int): Int = a + b
def curriedAdd(a: Int)(b: Int): Int = a + b
uncurriedAdd(40,2) == 42
curriedAdd(40)(2) == 42
val curriedResult = curriedAdd(40)_ // returns the "second" function which takes a single Int, returning the sum of it with 40
curriedResult(2) == 42
curriedResult(-40) == 0
/*
Curried functions allow you to implement your own control structures.
Since parameter lists can be surrounded by curly braces instead of parentheses, these structures can look very similar to native structures
 */
import java.io.PrintWriter
import java.io.File
def withPrintWriter(file: File)(op: PrintWriter => Unit): Unit = {
  val writer = new PrintWriter(file)
  try {
    op(writer)
  } finally {
    writer.close()
  }
}
//withPrintWriter(new File("example.txt")) {
//  printWriter => printWriter.print("This function body looks similar to the body of a built-in control structure.")
//}
/*
By-name parameters allow you to create parameter functions which take no arguments and are not evaluated until their first usage.
 */
var assertsEnabled = true
def byNameAssert(predicate: => Boolean) = if (assertsEnabled && !predicate) throw new AssertionError // no parameters specified
byNameAssert(5 > 3) // no function literal notation needed
assertsEnabled = false
byNameAssert(throw new Exception) // exception never thrown, since expression not evaluated until first use


/*
---
Chapter 10: Composition and Inheritance

In this chapter, the author builds a sample library for constructing and rendering layout elements in 2 dimensions.

Abstract classes have one or more methods with no implementation provided, and must be defined with the "abstract" keyword. They are not instantiable.
Concrete methods are referred to as "defined" while abstract methods are "declared".

Methods which take no parameters and do not change state omit the parentheses; this supports the Uniform Access Principle which says client code should not be affected by the decision to use a field or a method for attributes.
*/
abstract class Element {
  def contents: Array[String]
  def width: Int = if (contents.isEmpty) 0 else contents(0).length
  def height: Int = contents.length
  def above(that: Element): Element = { // must resize to fit largest element
    val this1 = this widen that.width
    val that1 = that widen this.width
    new ArrayElement(this1.contents ++ that1.contents)
  }
  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    new ArrayElement(
      for ((line1, line2) <- this1.contents zip that1.contents)
        yield line1 + line2
    )
  }

  override def toString = contents mkString "\n"

  private def widen(w: Int): Element = {
    if (w <= width) this
    else {
      val lpad = (w-width) / 2
      val rpad = w - (width + lpad)
      new ArrayElement(
        for (line <- contents)
          yield spaces(lpad) + line + spaces(rpad)
      )
    }
  }
  private def heighten(h: Int): Element = {
    if (h <= height) this
    else {
      val tpad = (h - height) / 2
      val bpad = h - (height + tpad)
      new ArrayElement(lines(tpad) ++ contents ++ lines(bpad))
    }
  }
  private def spaces(x: Int) = new String(Array.fill(x)(' '))
  private def lines(x: Int) = Array.fill(x)("")
}
/*
The methods assert, assume, and require can be used to verify runtime conditions:
  - assert and assume throw AssertionErrors (assume is typically used by static analysis tools), indicating an error in the code
  - require throws an IllegalArgumentException, blaming the caller for passing invalid arguments (e.g. passing 0 into (x: Int) => 1/x)

Subclasses are defined using the "extends" modifier, and must either implement the abstract methods or also be abstract.
Subclasses inherit all members of a superclass except for private members and members overridden by the subclass.
  - a subclass can also change a parent member from a method to a field (or vice versa)
Instances of subclasses may be used in any place where an instance of the superclass is expected.
*/
class ArrayElement(conts: Array[String]) extends Element {
  def contents: Array[String] = conts // implementing abstract methods
}
class UniformElement(ch: Char, override val width: Int, override val height: Int) extends Element {
  private val line = new String(Array.fill(width)(ch))
  def contents = Array.fill(height)(line)
}
/*
Whereas Java has different namespaces for fields, methods, types, and packages, Scala has only two namespaces for values and types.

Class parameter fields can use "val" and "var" to make them class fields; modifiers like "override", "private", and "protected" can also be used.
 */
class ArrayElementBetter(val contents: Array[String]) extends Element {}
/*
When classes inherit from parameterized parent classes, they must specify the parameters in the signature.
 */
class LineElement(s: String) extends ArrayElement(Array(s)) { // Array(s) is used as a parameter to the ArrayElement constructor
  override def width = s.length
  override def height = 1
}
/*
If a client defines a subclass with a new member, and later the parent class is updated and contains an identical member, the subclass member would be used erroneously.
The override modifier prevents this problem: since the client code will contain the same member without the override modifier, the compiler will throw an error.

Factories can be constructed with singleton object or classes.
 */
object Element {
  def elem(contents: Array[String]): Element = new ArrayElement(contents)
  def elem(line: String): Element =  new LineElement(line)
  def elem(chr: Char, width: Int, height: Int) = new UniformElement(chr, width, height)
}

val col1 = Seq("This","is", "the", "left", "column").foldLeft(Element.elem(""))((curr, el) => curr.above(Element.elem(el)))
val col2 = Seq("This","column","is on the right").foldLeft(Element.elem(""))((curr, el) => curr.above(Element.elem(el)))
col1.beside(col2)
/*
The top of the class hierarchy in Scala is Any. There are two subclasses of Any:
  - AnyVal, which is the parent of all the built-in value classes (the Java primitives and Unit). AnyVal types support implicit conversions to rich classes which offer more functionality.
  - AnyRef, which is the parent of all classes. AnyRef is an alias for java.lang.Object, the parent class of all Java classes.

In Scala, == follows natural equality for value types, and aliases the equals method for reference types.
To specifically compare references, Scala has "eq" and "ne" operators.
 */
val str1 = new String("dog")
val str2 = new String("dog")
str1 == str2
!(str1 eq str2)
str1 ne str2
/*
Null is a subclass of every reference type, so any non-value type can be assigned a value of null.
Nothing is a subclass of every class. Usually, Nothing is used to signify abnormal behaviour.
 */
def divide(x: Int, y: Int): Int = {
  if (y==0) error("divide by zero") // error has a Nothing return type, which is a subclass of Int
  else x/y
}


/*
---
Chapter 11: Traits and Mixins

Traits are analogous to Java interfaces. A class can have multiple traits.
Like with inheritance, a class which has a trait inherits methods and fields from that trait, and an instance of that class can be used where an instance of the trait is expected.
When a class inherits another class, traits are specified using the "with" keyword. If there is no inherited class, the trait is specified with "extends"
 */
trait Printable {
  def print() = println(this)
}
trait Excited {
  override def toString = super.toString + "!"
}
class Frog extends Printable {
  override def toString = "I am a frog"
}
val frog = new Frog
frog.print() // "I am a frog"
val excitedFrog = new Frog with Excited // trait mixin at instantiation
excitedFrog.print() // "I am a frog!"
/*
Java interfaces cannot define concrete methods. This makes it difficult to make "thick" interfaces with many methods, since a class must implement every method of an interface it implements.
Scala traits allow for concrete methods to be defined within traits, allowing for thicker interfaces.

The Ordered trait is a good example of where traits do much better for thick interfaces.
Other than equality, there are four main comparison operators (<, >, <=, >=), all of which can be implemented in terms of a single less-than (or greater-than) function.
The Ordered trait declares an abstract comparison method, and then defines concrete methods for each operator using the comparison method, so you only need to implement one method.
Equaliity is not defined by the Ordered trait.

Traits can be used to optimize efficiency, for example by caching the hashCode so it must only be computed once.
The order that traits are included is important; each consecutive trait overrides any methods of the previous.
 */
abstract class BaseBookShelf(books: List[String]) {
  override def hashCode = {
    books.map(_.hashCode).sum
  }
}
trait HashCaching {
  private var cachedHash: Int = 0
  private var hashComputed: Boolean = false
  override def hashCode = {
    if (!hashComputed) {
      cachedHash = super.hashCode
      hashComputed = true
    }
    cachedHash
  }
}
trait HashScrambling {
  override def hashCode = {
    val original = super.hashCode
    def rl(i: Int) = Integer.rotateLeft(original, i)
    original ^ rl(8) ^ rl(16) ^ rl(24)
  }
}
// if HashCaching was specified before HashScrambling, then the unscrambled hash would be cached instead of the scrambled version - order matters
class BookShelf(val books: List[String]) extends BaseBookShelf(books) with Ordered[BookShelf] with HashScrambling with HashCaching {
  def compare(that: BookShelf): Int = this.books.length - that.books.length
  def equals(that: BookShelf): Boolean = compare(that) == 0
}
val smallShelf = new BookShelf(List("Lord of the Rings", "The Hobbit"))
val largeShelf = new BookShelf(List("A Game of Thrones", "Harry Potter", "Programming in Scala"))
smallShelf < largeShelf
!(smallShelf == largeShelf)
/*
A trait can extend another class; what this means is that a class which mixes the trait in must extend the parent of the trait.
Traits may also define abstract methods which can be called from other traits if another trait has already given a concrete definition for the method.
 */
trait Talker {
  def talk: String
}
trait Loud extends Talker {
  abstract override def talk: String = super.talk.toUpperCase + "!!!"
}
trait Repetitive extends Talker {
  abstract override def talk: String = super.talk + " " + super.talk
}
class FoodServer extends Talker {
  def talk: String = "Anything to drink for you?"
}
(new FoodServer).talk == "Anything to drink for you?"
(new FoodServer with Loud).talk == "ANYTHING TO DRINK FOR YOU?!!!"
(new FoodServer with Repetitive).talk == "Anything to drink for you? Anything to drink for you?"
(new FoodServer with Loud with Repetitive).talk == "ANYTHING TO DRINK FOR YOU?!!! ANYTHING TO DRINK FOR YOU?!!!"
(new FoodServer with Repetitive with Loud).talk == "ANYTHING TO DRINK FOR YOU? ANYTHING TO DRINK FOR YOU?!!!" // ordering matters
/*
Trait linearization is the process of resolving method invocations in the correct order based on the mixin order of traits. Traits avoid issues that exist with multiple inheritance.
Linearization is complicated, but the general rule is that a class is linearized before all of its superclasses and mixed-in traits.
 */
class Sup
trait Trait1 extends Sup
trait Trait2 extends Sup with Trait3
trait Trait3
class C extends Sup with Trait1 with Trait2
/*
Linearization of C: C -> Trait2 -> Trait3 -> Trait1 -> Sup -> AnyRef -> Any
C is the class being linearized, so it starts with C. Then, Trait2 is the last mixed in trait, so it has highest precedence.
Trait2 mixes in Trait3, so next is Trait3. Trait2 extends Sup, but Sup is extended by C, so it is not next.
Returning to C, Trait1 is the previous trait mixed in, so it is next. Then, Sup is next, followed by its parent classes.
 */


/*
---
Chapter 12: Case Classes and Pattern Matching

Case classes automatically generate convenient features for classes:
  - factory methods so that classes can be instantiated without "new" just using the class parameters
  - each class parameter is made a "val" so it's accessible as a field
  - the "natural" implementations of toString, hashCode, and equals are added
 */
abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
val v = Var("x") // no need for "new"
v.name == "x" // parameter is a field
val v2 = Var("x")
v == v2 // natural implementation of equals
/*
Case classes make pattern matching easy. In Scala, match expressions are analogous to switch statements.
Cases are evaluated top to bottom, and they do not fall through.
Using literals in cases matches exact values, while using expressions like e match anything and introduce identifiers for the fields.
Underscores also match anything, but do not introduce identifiers to refer to fields.
 */
def simplifyTop(expr: Expr) : Expr = expr match {
  case UnOp("-", UnOp("-", e)) => e // double negation
  case BinOp("+", e, Number(0)) => e // additive identity
  case BinOp("*", e, Number(1)) => e // multiplicative identity
  case _ => expr // default case
}
simplifyTop(simplifyTop(UnOp("-", UnOp("-", BinOp("*", Var("x"), Number(1)))))) == Var("x") // - ( - ( x * 1 ) ) == x
/*
Note that in the above example, there are numerous layers of matching happening.
For example, with the first case, first it checks if expr is of type UnOp. It also checks if its second argument is an UnOp, and then if the first argument of that UnOp is "-".

Variable identifiers act as wildcards, so in order to use variables, you can:
  - use capitalized identifiers
  - use fields of an object
  - use back ticks
 */
val Num = Number(1)
val num2 = Number(2)
val num3 = Number(3)
def matchVar(expr: Expr): String = expr match {
  case Num => "matches with Num"
  case Number(num2.num) => "matches with num2"
  case `num3` => "matches with num3"
  case _ => "matches with none"
}
matchVar(Number(1)) == "matches with Num"
matchVar(Number(2)) == "matches with num2"
matchVar(Number(3)) == "matches with num3"
matchVar(Number(4)) == "matches with none"
/*
Pattern matching also works on structures like sequences and tuples, and can also check types.
 */
def identifyAny(x: Any): String = x match {
  case List(a, _*) => s"list starting with $a" // use of variable length arguments
  case (a,b,c) => s"3-tuple consisting of $a, $b, and $c"
  case a: Int => s"integer with the value $a"
  case a: Map[_,_] => "some kind of map"
  case _ => "something else"
}
identifyAny(List(1,2,3)) == "list starting with 1"
identifyAny((true, "foo", 42)) == "3-tuple consisting of true, foo, and 42"
identifyAny(17) == "integer with the value 17"
identifyAny(new scala.collection.immutable.HashMap[String, Int]) == "some kind of map"
identifyAny(42.0f) == "something else"
/*
For generics, Scala uses erasure, which means, while the types are known at compile-time, at runtime they are unknown.
Consequently, it is impossible to use specific generic types when pattern matching, except for Arrays which are implemented differently (as in Java).

If you need to bind a variable to a matched case class, it can be done with the @ modifier.
 */
def removeAbs(expr: Expr) = expr match {
  case UnOp("abs", e@UnOp("abs", _)) => e // remove redundant absolute value operations
  case _ => expr
}
/*
Pattern guards allow for extra conditions to be checked when matching.

In many cases, especially with tree structures, it makes sense to use recursion on the match result.
 */
def simplifyAdd(expr: Expr): Expr = expr match { // converts x + x to 2*x
  case BinOp("+", left, right) if left == right => BinOp("*", simplifyAdd(left), Number(2)) // BinOp("+", x, x) would not compile since it reuses an identifier
  case _ => expr
}
simplifyAdd(BinOp("+", Var("x"), Var("x"))) == BinOp("*", Var("x"), Number(2))
simplifyAdd(BinOp("+", BinOp("+", Var("x"), Var("x")), BinOp("+", Var("x"), Var("x")))) == BinOp("*", BinOp("*", Var("x"), Number(2)), Number(2))
/*
When writing pattern matches, it's important to cover every possible case to avoid match errors.
Sometimes, a default case does not make much sense, but it's impossible to enumerate all cases for a class if the class can be extended.
Sealed classes are classes which can only be extended from within the same file. Thus, sealed classes have a guarantee about possible patterns.
It is recommended to seal classes being used for pattern matching; the compiler will flag matches missing certain patterns.

For example, if Expr was made sealed, the compiler would throw a warning if any match expression on an Expr did not have a case for each subclass of Expr.

The Option class provides a way to handle optional values more cleanly than null-checking, since handling these optional values is checked compile-time.
An Option[A] instance may be Some[A] or None. Pattern matching is useful in handling either case.
 */
val translations: Map[String, String] = Map("blue" -> "bleu", "red" -> "rouge", "green" -> "vert")
def lookupIfExists(key: String): String = translations.get(key) match { // the get method returns an Option
  case Some(x) => x
  case None => "key not found in map"
}
lookupIfExists("green") == "vert"
lookupIfExists("yellow") == "key not found in map"
/*
Patterns can be used to deconstruct tuples and case classes. Wildcards can be used to ignore fields.
 */
val tup = (42, "apple", 3.0)
val (number, fruit, float) = tup
number == 42
fruit == "apple"
float == 3.0

case class Flight(dep: String, dest: String, time: String)
val flight1 = Flight("YYZ", "YTZ", "12:00pm")
val Flight(_, dest, _) = flight1
dest == "YTZ"
/*
Match expressions are partial functions. To check whether a PartialFunction can be evaluated at some value, you can use the isDefinedAt function.
 */
val pf: PartialFunction[String, String] = {
  case "blue" => "bleu"
  case "red" => "rouge"
  case "green" => "vert"
}
pf.isDefinedAt("green")
!pf.isDefinedAt("yellow")
/*
Pattern matching also works with for expressions.
 */
val reverseTranslations = for ((english, french) <- translations) yield french -> english
reverseTranslations("bleu") == "blue"
val options = List(Some("apple"), None, Some("orange"))
(for (Some(option) <- options) yield option) == List("apple", "orange")
