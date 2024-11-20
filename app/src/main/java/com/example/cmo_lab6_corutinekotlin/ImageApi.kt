package com.example.cmo_lab6_corutinekotlin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ImageApi {
    @GET("index.json")
    suspend fun getImages(): List<String>

    companion object {
        private const val BASE_URL = "http://cti.ubm.ro/cmo/digits/"

        fun create(): ImageApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ImageApi::class.java)
        }
    }
}
