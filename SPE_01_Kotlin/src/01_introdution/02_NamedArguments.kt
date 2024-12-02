package `01_introdution`

fun joinOptions(options: Collection<String>) =
    options.joinToString(", ", "[", "]")

fun main(){
    println(joinOptions(listOf("a","b","c")))
}