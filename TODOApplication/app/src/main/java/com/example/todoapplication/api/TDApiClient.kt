package com.example.todoapplication.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TDApiClient {
    val service: TDApiInterface
    val okHttpClient: OkHttpClient

    init {
        okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val originalRequest = chain.request()
                    val response = chain.proceed(originalRequest)
                    return@Interceptor response
                })
                        connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
        }.build();
        val client = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = client.create(TDApiInterface::class.java)
    }
}