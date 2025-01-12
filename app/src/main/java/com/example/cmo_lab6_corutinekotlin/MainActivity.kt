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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.cmo_lab6_corutinekotlin.ui.theme.CMO_Lab6_CorutineKotlinTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
        is ImageState.Loading -> LoadingIndicator()
        is ImageState.Error -> ErrorMessage((imageState as ImageState.Error).message)
        is ImageState.Success -> {
            val images = (imageState as ImageState.Success).images
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize()
            ) {
                items(images.size) { index ->
                    Image(
                        bitmap = images[index].asImageBitmap(),
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
    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
}

@Composable
fun ErrorMessage(message: String) {
    Text(
        text = "Error: $message",
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.h6
    )
}

