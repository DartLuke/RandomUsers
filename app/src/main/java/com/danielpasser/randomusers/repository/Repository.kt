package com.danielpasser.randomusers.repository

import android.util.Log
import com.danielpasser.randomusers.api.Api
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.room.UserDao
import com.danielpasser.randomusers.utils.DataState
import com.danielpasser.randomusers.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository constructor(
    private val retrofit: Api,
    private val userDao: UserDao
) {


    private val mapper = Mapper()


    suspend fun getUsers(getFromDb: Boolean): Flow<DataState<List<User>>> = flow {

        emit(DataState.Loading)
        try {
            var users: List<User> = arrayListOf()
            users = if (getFromDb) {
                Log.v("Test", "Get from from DB")
                val usersDb = userDao.gets()
                if (usersDb.isEmpty())
                    downloadUsers()
                else
                    mapper.userDbEntityToUserList(usersDb)
            } else {
                Log.v("Test", "Get from from Server")
                downloadUsers()

            }
            emit(DataState.Success(users))
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            emit(DataState.Error(e))
        }
    }

    private suspend fun downloadUsers(): List<User> {
        val users = retrofit.getUsers(10).results
        userDao.delete()
        for (user in users) userDao.insert(mapper.userToUserDbEntity(user))
        Log.v("Test","Download Users")
        return users
    }


}