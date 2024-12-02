package `04_collections`

// Return the set of products that were ordered by all customers
fun Shop.getProductsOrderedByAll(): Set<Product> =
    customers.map{it.getOrderedProductsSet()}.reduce{
            setOrdered, customer -> setOrdered.intersect(customer)}

fun Customer.getOrderedProductsSet(): Set<Product> =
    orders.flatMap{it.products}.toSet()