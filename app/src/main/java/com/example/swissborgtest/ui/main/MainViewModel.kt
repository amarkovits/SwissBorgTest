package com.example.swissborgtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swissborgtest.model.OrderBook
import com.example.swissborgtest.model.OrderBookData
import com.example.swissborgtest.model.Ticker
import com.example.swissborgtest.network.BitfinexClient
import com.example.swissborgtest.repository.BitfinexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val bitfinexRespository: BitfinexRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _ticker = MutableLiveData<Ticker>()
    val ticker: LiveData<Ticker>
        get() = _ticker

    private val _orderBooks = MutableLiveData<List<OrderBook>>()
    val orderBooks: LiveData<List<OrderBook>>
        get() = _orderBooks

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
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}