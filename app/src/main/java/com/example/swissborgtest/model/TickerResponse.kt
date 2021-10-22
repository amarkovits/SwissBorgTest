package com.example.swissborgtest.model

data class TickerResponse(
    val event: String,
    val channel: String,
    val chanId: String,
    val pair: String
)