package com.danielpasser.randomusers.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDbEntity::class], version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        val DATABASE_NAME: String = "user_db"
    }
}