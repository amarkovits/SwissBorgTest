package com.example.swissborgtest.repository

import com.example.swissborgtest.model.OrderBook
import com.example.swissborgtest.model.OrderBookSetup
import com.example.swissborgtest.model.OrderBookUpdate
import com.example.swissborgtest.model.Ticker
import com.example.swissborgtest.network.BitfinexClient
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import java.util.*
import java.util.concurrent.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitfinexRepository @Inject constructor(private val bitfinexClient: BitfinexClient) {

    fun getOrderBook(): Flowable<List<OrderBook>> {
        val orderBooks = TreeMap<Double, OrderBook>()
        return bitfinexClient.getOrderBook().map {
            when (it) {
                is OrderBookSetup -> {
                    orderBooks.clear()
                    it.orderBooks.forEach { orderBook ->
                        orderBooks[orderBook.price] = orderBook
                    }
                }
                is OrderBookUpdate -> {
                    it.orderBook.let { orderBook ->
                        if (orderBook.count == 0) {
                            orderBooks.remove(orderBook.price)
                        } else {
                            orderBooks[orderBook.price] = orderBook
                        }
                    }
                }
            }
            orderBooks.values.toList()
        }
    }

    fun getTicker(): Flowable<Ticker> {
        return bitfinexClient.getTicker()
    }

    fun getConnected(): Flowable<Boolean> {
        return bitfinexClient.getConnectionState()
            .map { it is WebSocket.Event.OnConnectionOpened<*> }
    }

}