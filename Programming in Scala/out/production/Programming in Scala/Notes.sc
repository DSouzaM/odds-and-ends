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
object example {
  def main(args: Array[String]): Unit = {
    args.foreach(println)
  }
}