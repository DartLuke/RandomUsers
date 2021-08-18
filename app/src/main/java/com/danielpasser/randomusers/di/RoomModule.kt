package com.danielpasser.randomusers.di

import android.content.Context
import androidx.room.Room
import com.danielpasser.randomusers.room.UserDao
import com.danielpasser.randomusers.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule     {
    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context) :UserDatabase =
        Room.databaseBuilder(context,UserDatabase::class.java,UserDatabase.DATABASE_NAME).
        fallbackToDestructiveMigration().
        build()

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase):UserDao=userDatabase.userDao()
}