package com.example.test.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CleanUriApi {

    @FormUrlEncoded
    @POST("api/v1/shorten")
    suspend fun shortenUrl(
        @Field("url") url: String
    ): CleanUriResponse


    companion object{
        @JvmStatic
        fun prepare() : CleanUriApi{
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl("https://cleanuri.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CleanUriApi::class.java)
        }
    }
}