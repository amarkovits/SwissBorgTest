package com.example.swissborgtest.network

import com.example.swissborgtest.model.OrderBookRequest
import com.example.swissborgtest.model.OrderBookResponse
import com.example.swissborgtest.model.TickerRequest
import com.example.swissborgtest.model.TickerResponse
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface BitfinexService {

    @Receive
    fun openWebSocket(): Flowable<WebSocket.Event>

    @Receive
    fun receiveTickerResponse(): Flowable<TickerResponse>

    @Send
    fun sendTickerRequest(subscribeTickerRequest: TickerRequest)

    @Receive
    fun observeTicker(): Flowable<Array<String>>

    @Receive
    fun receiveOrderBookResponse(): Flowable<OrderBookResponse>

    @Send
    fun sendOrderBookRequest(subscribeOrderBookRequest: OrderBookRequest)

    @Receive
    fun observeOrderBook(): Flowable<FloatArray>

}