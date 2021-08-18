package com.danielpasser.randomusers.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("DELETE FROM users")
    suspend fun delete()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDbEntity: UserDbEntity): Long

    @Query("SELECT * FROM users")
    suspend fun gets(): List<UserDbEntity>
}