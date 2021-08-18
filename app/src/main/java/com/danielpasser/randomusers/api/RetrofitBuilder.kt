package com.danielpasser.randomusers.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


object RetrofitBuilder {

    private fun client() :OkHttpClient
    {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private fun retrofit() =
        Retrofit.Builder().baseUrl("https://randomuser.me/api/").client(client())
            .addConverterFactory(GsonConverterFactory.create()).build()

    val api: Api = retrofit().create(Api::class.java)

}