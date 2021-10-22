package com.example.swissborgtest.model

data class Ticker(
    val channelId: Int,
    val bid: Float,
    val bidSize: Float,
    val ask: Float,
    val askSize: Float,
    val dailyChange: Float,
    val dailyChangePerc: Float,
    val lastPrice: Float,
    val volume: Float,
    val high: Float,
    val low: Float
)

fun Array<String>.toTicker(): Ticker {
    return Ticker(
        get(0).toInt(),
        get(1).toFloat(),
        get(2).toFloat(),
        get(3).toFloat(),
        get(4).toFloat(),
        get(4).toFloat(),
        get(4).toFloat(),
        get(4).toFloat(),
        get(4).toFloat(),
        get(4).toFloat(),
        get(4).toFloat()
    )
}


