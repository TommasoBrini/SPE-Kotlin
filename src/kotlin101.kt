
fun foo(a:Int = 0, b:String = "foo") = println("OK")

fun main(){

    foo(1, "bar") // OK, positional
    foo(a = 1, b = "bar") // OK, named
    foo(1, b = "bar") // OK hybrid
    foo(a = 1, "bar") // error // in realtà OK
    foo()
    // foo("bar") error: type mismatch
    foo(b="bar")

    val batman = "batman!"

    print("${Double.NaN}".repeat(10) + " " + batman)



}