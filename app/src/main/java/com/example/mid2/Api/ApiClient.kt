package com.example.mid2.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * singleton for api
 */
object ApiClient {
    // api url
    private const val API_BASE_URL = "https://jsonplaceholder.typicode.com/"

    // creating retrofit object
    private val retroFit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // creating api service
    val apiService: ApiService = retroFit.create(ApiService::class.java)
}