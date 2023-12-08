package com.example.mid2.Api

import com.example.mid2.Model.UserEntity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    // getting all users from api
    @GET("users")
    suspend fun getAllUsers(): Response<List<UserEntity>>
}