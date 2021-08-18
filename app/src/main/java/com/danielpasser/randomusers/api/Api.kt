package com.danielpasser.randomusers.api

import com.danielpasser.randomusers.models.UserResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @Headers("Content-Type: application/json")
    @GET("/api/")
    suspend fun getUsers(
        @Query("results") results:Int

        ):UserResult



}