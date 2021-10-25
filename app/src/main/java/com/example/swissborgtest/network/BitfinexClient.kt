package com.example.swissborgtest.network

import com.example.swissborgtest.model.*
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitfinexClient @Inject constructor(private val bitfinexService: BitfinexService) {

    fun getTicker(): Flowable<Ticker> {
        bitfinexService.openWebSocket()
            .filter {
                it is WebSocket.Event.OnConnectionOpened<*>
            }
            .subscribe({
                Timber.d("connected")
                val request = TickerRequest(
                    "subscribe",
                    "ticker",
                    "BTCUSD"
                )
                bitfinexService.sendTickerRequest(request)
            }, {
                Timber.e(it)
            })
        return bitfinexService.observeTicker().filter {
            it.size == 11
        }.map {
            it.toTicker()
        }
    }

    fun getOrderBook(): Flowable<OrderBookData> {
        bitfinexService.openWebSocket()
            .filter {
                it is WebSocket.Event.OnConnectionOpened<*>
            }
            .subscribe({
                val request = OrderBookRequest(
                    "subscribe",
                    "book",
                    "BTCUSD"
                )
                bitfinexService.sendOrderBookRequest(request)
            }, {
                Timber.e(it)
            })
        return bitfinexService.observeOrderBook().filter {
            it.isOrderBookSetup() || it.isOrderBookUpdate()
        }.map {
            if (it.isOrderBookSetup()) {
                it.toOrderBookSetup()
            } else {
                it.toOrderBookUpdate()
            }
        }
    }

    fun getConnectionState(): Flowable<WebSocket.Event> {
        return bitfinexService.openWebSocket()
            .filter {
                Timber.d("event=$it")
                it !is WebSocket.Event.OnMessageReceived
            }
    }

}