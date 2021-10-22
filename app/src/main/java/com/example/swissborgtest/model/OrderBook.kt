package com.example.swissborgtest.model

data class OrderBook(
    private val precision: String,
    private val frequency: String,
    private val price: Float,
    private val count: Int,
    private val amount: Float,
    private val length: String
)

fun FloatArray.toOrderBook(): OrderBook {
    return OrderBook(
        "", "", 0f, 0, 0f, ""
    )
}