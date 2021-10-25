package com.example.swissborgtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.swissborgtest.model.OrderBook
import com.example.swissborgtest.model.OrderBookData
import com.example.swissborgtest.model.Ticker
import com.example.swissborgtest.network.BitfinexClient
import com.example.swissborgtest.repository.BitfinexRepository
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bitfinexRespository: BitfinexRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _ticker = MutableLiveData<Ticker>()
    val ticker: LiveData<Ticker>
        get() = _ticker

    private val _orderBooks = MutableLiveData<List<OrderBook>>()
    val askOrderBooks: LiveData<List<OrderBook>>
        get() = _orderBooks.map { list ->
            list.filter { it.amount < 0 }.sortedBy { it.price }
        }
    val bidOrderBooks: LiveData<List<OrderBook>>
        get() = _orderBooks.map { list ->
            list.filter { it.amount > 0 }.sortedByDescending { it.price }
        }

    private val _connected = MutableLiveData<Boolean>()
    val connected: LiveData<Boolean>
        get() = _connected

    init {
        compositeDisposable.add(
            bitfinexRespository.getOrderBook().subscribe({
                _orderBooks.postValue(it)
            }, {
                Timber.e(it)
            })
        )
        compositeDisposable.add(
            bitfinexRespository.getTicker().subscribe({
                _ticker.postValue(it)
            }, {
                Timber.e(it)
            })
        )
        compositeDisposable.add(
            bitfinexRespository.getConnected().subscribe({
                _connected.postValue(it)
            }, {
                Timber.e(it)
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
        compositeDisposable.clear()
    }

}