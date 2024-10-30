package com.example.autopartsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    secondary = OrangeSecondary,
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray
)

private val DarkColorScheme = darkColorScheme(
    primary = OrangeSecondary,
    onPrimary = Color.Black,
    secondary = OrangePrimary,
    onSecondary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = DarkGray,
    onSurface = Color.White
)

@Composable
fun AutoPartsAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
