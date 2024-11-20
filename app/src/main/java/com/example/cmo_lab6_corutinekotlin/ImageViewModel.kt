package com.example.cmo_lab6_corutinekotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {
    private val _images = MutableStateFlow<ImageState>(ImageState.Loading)
    val images: StateFlow<ImageState> = _images

    private val imageApi = ImageApi.create()  // Instanțiem ImageApi

    init {
        loadImages()
    }

    private fun loadImages() {
        // Lansează un apel de rețea într-o corutină
        viewModelScope.launch {
            try {
                // Apelăm API-ul pentru a obține lista de imagini
                val imageList = imageApi.getImages()
                _images.value = ImageState.Success(imageList)
            } catch (e: Exception) {
                _images.value = ImageState.Error(e.message ?: "An error occurred")
            }
        }
    }
}

sealed class ImageState {
    object Loading : ImageState()
    data class Success(val images: List<String>) : ImageState()
    data class Error(val message: String) : ImageState()
}
