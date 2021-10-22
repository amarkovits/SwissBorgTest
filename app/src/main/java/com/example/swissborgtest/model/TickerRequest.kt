package com.example.swissborgtest.model

data class TickerRequest(
    val event: String,
    val channel: String,
    val pair: String
)