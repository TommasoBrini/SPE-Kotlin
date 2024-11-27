package `01_introdution`

fun containsEven(collection: Collection<Int>): Boolean =
    collection.any { x -> (x % 2 == 0) }

fun main(){
    println(containsEven(listOf(3,7,5,7)))
}