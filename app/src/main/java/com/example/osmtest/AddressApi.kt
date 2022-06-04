package com.example.osmtest

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApi {
    @GET("/search")
    suspend fun getAddress(@Query("q") query: String): Response<List<Address>>
}