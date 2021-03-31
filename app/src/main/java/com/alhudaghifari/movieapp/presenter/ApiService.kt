package com.alhudaghifari.movieapp.presenter

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ApiService {
    companion object {
        fun create(): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(ApiConstant().TIMEOUT_API_SERVICE, TimeUnit.SECONDS)
                .connectTimeout(ApiConstant().TIMEOUT_API_SERVICE, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(ApiConstant().server)
                .build()
            return retrofit
        }
    }
}