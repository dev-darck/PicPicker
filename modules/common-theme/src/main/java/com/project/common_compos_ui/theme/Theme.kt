package com.project.common_compos_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColors = lightColors(
    primary = White,
    secondary = Red,
    onSecondary = Gray88,
    background = White,
    surface = White,
)

private val DarkColors = darkColors(
    primary = Black,
    secondary = Red,
    onSecondary = Gray88,
    background = Black,
    surface = Black,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        shapes = AppShape,
        colors = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}