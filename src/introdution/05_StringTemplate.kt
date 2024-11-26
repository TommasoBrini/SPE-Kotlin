package introdution

val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

// pattern string like => 13 JUN 2021
fun getPattern(): String = """\d{2} $month \d{4}"""

fun main(args: Array<String>) {
    val pattern = Regex(getPattern())
    println(pattern.matches("25 DEC 2023"))
    println(pattern.matches("24.03.3000"))
}