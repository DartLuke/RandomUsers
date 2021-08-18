package com.danielpasser.randomusers.ui.repository

import com.danielpasser.randomusers.api.RetrofitBuilder
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository {



    suspend fun getUsers(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Loading)
        try{
            val result=RetrofitBuilder.api.getUsers(10).results
            emit(DataState.Success(result))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }



}