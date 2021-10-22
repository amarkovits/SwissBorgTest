package com.example.swissborgtest.model

data class OrderBookRequest(
    val event: String,
    val channel: String,
    val pair: String
)
