package com.example.swissborgtest.model

data class OrderBookResponse(
    val event: String,
    val channel: String,
    val chanId: String,
    val pair: String,
    val prec: String,
    val freq: String,
    val len: String
) {
}