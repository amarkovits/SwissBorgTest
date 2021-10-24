package com.example.swissborgtest.model

data class OrderBook(
    val price: Double,
    val count: Int,
    val amount: Double,
)

fun Array<*>.toOrderBook(): OrderBook {
    return OrderBook(get(1) as Double, (get(2) as Double).toInt(), get(3) as Double)
}

fun ArrayList<*>.toOrderBook(): OrderBook {
    return OrderBook(get(0) as Double, (get(1) as Double).toInt(), get(2) as Double)
}