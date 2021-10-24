package com.example.swissborgtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.swissborgtest.network.BitfinexClient
import com.example.swissborgtest.repository.BitfinexRepository
import com.example.swissborgtest.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bitfinexRepository: BitfinexRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        Timber.d("onCreate")
        Log.d("MainActitivy", "create")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        bitfinexRepository.getTicker().subscribe({
            Timber.d("ticker=$it")
        }, {
            Timber.e(it)
        })

        bitfinexRepository.getOrderBook().subscribe({
            Timber.d("orderBookSize=${it.size}")
        }, {
            Timber.e(it)
        })
    }
}