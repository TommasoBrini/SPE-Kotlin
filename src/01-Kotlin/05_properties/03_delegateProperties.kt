package `05_properties`

class LazyPropertyDelegate(val initializer: () -> Int) {
    val lazyValue: Int by lazy(initializer)
}