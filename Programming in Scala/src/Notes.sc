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


 */