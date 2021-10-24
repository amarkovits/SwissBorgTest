package com.example.swissborgtest.model

abstract class OrderBookData {
}

class OrderBookUpdate(val orderBook: OrderBook) : OrderBookData()

class OrderBookSetup(val orderBooks: List<OrderBook>) : OrderBookData()

fun Array<*>.toOrderBookSetup(): OrderBookSetup {
    return OrderBookSetup((get(1) as ArrayList<*>).map { (it as ArrayList<*>).toOrderBook() })
}

fun Array<*>.toOrderBookUpdate(): OrderBookUpdate {
    return OrderBookUpdate(this.toOrderBook())
}

fun Array<*>.isOrderBookSetup(): Boolean {
    return (get(0) is Double) && (get(1) is ArrayList<*>)
}

fun Array<*>.isOrderBookUpdate(): Boolean {
    return (get(0) is Double) && (get(1) is Double) && (size == 4)
}
