package com.danielpasser.randomusers.di

import com.danielpasser.randomusers.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder().baseUrl("https://randomuser.me/api/")
        .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit.Builder): Api = retrofit.build().create(Api::class.java)

}