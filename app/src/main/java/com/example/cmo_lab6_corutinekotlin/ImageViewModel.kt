package com.example.cmo_lab6_corutinekotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ImageViewModel : ViewModel() {
    private val _images = MutableStateFlow<ImageState>(ImageState.Loading)
    val images: StateFlow<ImageState> = _images
    private val client = OkHttpClient()

    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch {
            try {
                val urls = ImageApi.create().getImages()
                val bitmaps = urls.mapNotNull { url ->
                    downloadImage(url)
                }
                _images.value = ImageState.Success(bitmaps.take(9)) // Prindem doar primele 9 imagini
            } catch (e: Exception) {
                _images.value = ImageState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private suspend fun downloadImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val bytes = response.body?.bytes()
                    if (bytes != null) {
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    } else null
                } else null
            } catch (e: IOException) {
                Log.e("ImageViewModel", "Error downloading image: $url", e)
                null
            }
        }
    }
}



sealed class ImageState {
    object Loading : ImageState()
    data class Success(val images: List<Bitmap>) : ImageState() // Schimbat pentru List<Bitmap>.
    data class Error(val message: String) : ImageState()
}

