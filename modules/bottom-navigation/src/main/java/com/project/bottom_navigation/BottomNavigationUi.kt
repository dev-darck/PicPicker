package com.project.bottom_navigation

import androidx.compose.runtime.Composable

interface BottomNavigationUi {
    val screen: BottomNavigationEntry
    val icon: Int
    val isRoot: Boolean get() = false
    val order: Int
    val openScreen: @Composable () -> Unit
}