package `01_introdution`

fun foo(name: String = "", number: Int = 42, toUpperCase: Boolean = false) =
    (if (toUpperCase) name.uppercase() else name) + number

fun main(args: Array<String>) {
    println(foo("a"))
    println(foo("b", number = 1))
    println(foo("c", toUpperCase = true))
    println(foo(name = "d", number = 2, toUpperCase = true))
}