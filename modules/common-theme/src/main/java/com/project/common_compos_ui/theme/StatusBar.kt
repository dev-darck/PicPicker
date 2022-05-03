package com.project.common_compos_ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StatusBarColorProvider() {
    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colors.background
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }
}