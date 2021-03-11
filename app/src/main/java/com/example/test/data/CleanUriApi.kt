package com.example.test.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CleanUriApi {

    @FormUrlEncoded
    @POST("shorten")
    suspend fun shortenUrl(
        @Field("url") url: String
    ): CleanUriResponse


    companion object{
        @JvmStatic
        fun create() : CleanUriApi{
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl("https://cleanuri.com/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CleanUriApi::class.java)
        }
    }
}