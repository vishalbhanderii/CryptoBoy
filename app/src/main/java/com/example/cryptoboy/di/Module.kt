package com.example.cryptoboy.di

import android.content.Context
import com.example.cryptoboy.app.CryptoApp
import com.example.cryptoboy.socket.CoinbaseService
import com.example.cryptoboy.socket.FlowStreamAdapter
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.lifecycle.android.BuildConfig
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Implement all Hilt-Dagger Module
 */
@Module
@InstallIn(SingletonComponent::class)
class Module {

    /**
     * Scarlet
     */
    @Provides
    fun provideScarlet(application: CryptoApp, client: OkHttpClient, moshi: Moshi): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("wss://ws-feed.pro.coinbase.com"))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
            .build()
    }


    /**
     * Provide context as CryptoApp
     */
    @Provides
    fun providesApplication(@ApplicationContext context: Context): CryptoApp {
        return context as CryptoApp
    }


    /**
     * Moshi for json serialization
     */
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }


    /**
     * Okhttp
     */
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                else HttpLoggingInterceptor.Level.NONE
            )

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }


    /**
     * Services for scarlet
     */
    @Provides
    fun provideCoinbaseService(scarlet: Scarlet): CoinbaseService {
        return scarlet.create()
    }

}