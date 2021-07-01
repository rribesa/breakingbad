package com.samples.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebClient {
    private val gson: Gson by lazy { GsonBuilder().setLenient().create() }
    private val interceptor = HttpLoggingInterceptor()

    private fun okHttp(): OkHttpClient {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }


    fun retrofit(url: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(url)
        .client(okHttp())
        .build()

    inline fun <reified T> service(url: String): T = retrofit(url).create(
        T::class.java
    )
}