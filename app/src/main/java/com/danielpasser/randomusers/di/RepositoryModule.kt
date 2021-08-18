package com.danielpasser.randomusers.di

import com.danielpasser.randomusers.api.Api
import com.danielpasser.randomusers.repository.Repository
import com.danielpasser.randomusers.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(retrofit: Api, userDao: UserDao) : Repository=Repository(retrofit,userDao)
}