class Foo(var a: String, var b: Int, greeting: String = "default"){
    init {
        println(greeting)
    }
}

class Prova(val b: String){
    constructor(b: Long): this("$b stringa")
    constructor(b: Int): this(b.toLong())
}

class Son(val f: Father)

class Father {lateinit var s: Son}

open class A
class B: A()


fun main(args: Array<String>) {
    Foo("ba", 1)

    println(Prova(1).b)

    val father = Father()
    //father.s
    val son = Son(father)
    father.s = son
    father.s.f

}