package com.project.navigation.bottomnav

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

interface BottomNavigationUi {
    val screen: BottomNavigationEntry
    val icon: ImageVector
    val isRoot: Boolean get() = false
    val order: Int
    val openScreen: @Composable () -> Unit
}