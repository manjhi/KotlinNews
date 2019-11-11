package com.omninos.kotlinproject.data.network

import com.omninos.kotlinproject.data.network.responses.NewsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApi {

    @GET("v2/top-headlines?country=us&category=business&apiKey=b39d7924ea4143aaa29e2bed2c2054ad")
    suspend fun getData(): Response<NewsResponse>

    companion object {
        operator fun invoke(
        ): MyApi {
            return Retrofit
                .Builder().baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}