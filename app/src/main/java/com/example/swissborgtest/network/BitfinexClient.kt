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
                Timber.d("ticker event=$it")
                it is WebSocket.Event.OnConnectionOpened<*>
            }
            .subscribe({
                Timber.d("socket opened")
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
                Timber.d("order event=$it")
                it is WebSocket.Event.OnConnectionOpened<*>
            }
            .subscribe({
                Timber.d("socket opened")
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
            Timber.d("filter $it")
            it.isOrderBookSetup() || it.isOrderBookUpdate()
        }.map {
            Timber.d("map")
            if (it.isOrderBookSetup()) {
                it.toOrderBookSetup()
            } else {
                it.toOrderBookUpdate()
            }
        }
    }

}