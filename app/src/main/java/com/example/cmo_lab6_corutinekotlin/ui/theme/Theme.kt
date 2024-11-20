package com.example.cmo_lab6_corutinekotlin.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme // Folosim Material 2
import androidx.compose.material.darkColors // Folosim darkColors și lightColors din Material 2
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Definirea culorilor pentru tema Material2
private val DarkColorScheme = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80,
    background = Color.Black,
    surface = Color.DarkGray
)

private val LightColorScheme = lightColors(
    primary = Purple40,
    secondary = PurpleGrey40,
    background = Color.White,
    surface = Color.LightGray
)

@Composable
fun CMO_Lab6_CorutineKotlinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Alege culoarea temei pe baza modului întunecat/luminos
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb() // Setează culoarea pentru bara de stare
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    // Folosește MaterialTheme din material2
    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}