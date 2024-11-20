package com.example.cmo_lab6_corutinekotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface // Import corect
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.cmo_lab6_corutinekotlin.ui.theme.CMO_Lab6_CorutineKotlinTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Folosim tema implicită fără culori personalizate
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background // Folosim tema implicită
                ) {
                    ImageGrid(viewModel)
                }
            }
        }
    }
}

@Composable
fun ImageGrid(viewModel: ImageViewModel) {
    val imageState by viewModel.images.collectAsState()

    when (imageState) {
        is ImageState.Loading -> {
            // Afișează un indicator de încărcare
            LoadingIndicator()
        }
        is ImageState.Error -> {
            // Afișează mesajul de eroare
            ErrorMessage((imageState as ImageState.Error).message)
        }
        is ImageState.Success -> {
            val images = (imageState as ImageState.Success).images
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize()
            ) {
                items(images.size) { index ->
                    val imageUrl = "http://cti.ubm.ro/cmo/digits/${images[index]}"
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Digit Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    // Afișează un indicator de încărcare
    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
}

@Composable
fun ErrorMessage(message: String) {
    // Afișează mesajul de eroare
    Text(text = "Error: $message")
}
