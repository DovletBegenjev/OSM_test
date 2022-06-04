package com.example.osmtest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: AddressApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://geocode.maps.co")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AddressApi::class.java)
    }
}