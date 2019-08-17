/*
 * Created by Muhammad Utsman on 8/18/19 3:55 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/18/19 3:31 AM
 */

package com.utsman.recycling.sample

import com.utsman.recycling.core.BASE_URL
import com.utsman.recycling.core.HEADER
import com.utsman.recycling.core.Responses
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RetrofitInstance {

    @Headers(HEADER)
    @GET("v1/curated")
    fun getCuratedPhoto(
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Call<Responses>


    companion object {
        fun create(): RetrofitInstance {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(RetrofitInstance::class.java)
        }
    }

}