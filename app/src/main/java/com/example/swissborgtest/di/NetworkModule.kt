package com.example.swissborgtest.di

import android.app.Application
import android.content.Context
import com.example.swissborgtest.MainActivity
import com.example.swissborgtest.network.BitfinexService
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideBitfinexService(@ApplicationContext appContext: Context): BitfinexService {
        val client = OkHttpClient.Builder().build()
        val gson = Gson()
        return Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("wss://api.bitfinex.com/ws/1"))
            .addMessageAdapterFactory(GsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .lifecycle(AndroidLifecycle.ofApplicationForeground(appContext as Application))
            .build()
            .create()
    }
}