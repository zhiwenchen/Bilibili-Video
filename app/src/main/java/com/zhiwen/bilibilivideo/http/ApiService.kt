package com.zhiwen.bilibilivideo.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// object 用于创建单例对象
object ApiService {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://8.136.122.222/jetpack/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService() : ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}