package com.pedroroig.remote.service

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GithubTrendingServiceFactory {

    fun makeGithubTrendingService(isDebug: Boolean) =
        makeGithubTrendingService(
            makeOkHttpClient(
                makeLoggingInterceptor(isDebug)
            )
        )

    private fun makeGithubTrendingService(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubTrendingService::class.java)

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

    private fun makeLoggingInterceptor(isDebug: Boolean) =
        HttpLoggingInterceptor().apply {
            level =
                if(isDebug)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
        }

}