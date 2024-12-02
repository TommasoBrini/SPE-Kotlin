
fun foo(a:Int = 0, b:String = "foo") = println("OK")

fun printAll(vararg strings: String){
    strings.forEach{println(it)}
}

fun main(){

    foo(1, "bar") // OK, positional
    foo(a = 1, b = "bar") // OK, named
    foo(1, b = "bar") // OK hybrid
    foo(a = 1, "bar") // error // in realtà OK
    foo()
    // foo("bar") error: type mismatch
    foo(b="bar")

    val batman = "batman!"

    println("${Double.NaN}".repeat(10) + " " + batman)
    println("Batman is $batman.length characters long")
    println("Batman is ${batman.length} characters long")

    val dante = """
         Tanto gentile e tanto onesta pare
         la donna mia quand'ella altri saluta,
         ch'ogni lingua deven, tremando, muta
         e li occhi non l'ardiscon di guardare.
    """.trimIndent()
    println(dante)
    // string raw utili per scrivere espressioni regolari
    val finalWordsEndingInA = """\W*(\w*a)\W*${'$'}""".toRegex(RegexOption.MULTILINE)
    println(finalWordsEndingInA.findAll(dante).map { it.groups[1]?.value }.toList())

    printAll("Lorem", "ipsum", "dolor", "amet")


}