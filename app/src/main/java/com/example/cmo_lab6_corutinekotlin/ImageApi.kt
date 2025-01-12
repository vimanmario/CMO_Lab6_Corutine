package com.example.cmo_lab6_corutinekotlin

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



import org.jsoup.Jsoup

interface ImageApi {
    suspend fun getImages(): List<String>

    companion object {
        fun create(): ImageApi {
            return ImageApiImpl()
        }
    }
}

class ImageApiImpl : ImageApi {
    override suspend fun getImages(): List<String> {
        return (0..9).map { i ->
            "http://cti.ubm.ro/cmo/digits/img$i.jpg"
        }
    }
}







