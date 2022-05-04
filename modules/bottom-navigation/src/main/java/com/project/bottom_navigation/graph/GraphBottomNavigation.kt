package com.project.bottom_navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.project.bottom_navigation.BottomNavigationUi

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addBottomNavigationDestinations(tabs: Set<BottomNavigationUi>) {
    tabs.forEach { entry ->
        val destination = entry.screen
        composable(destination.route) {
            entry.openScreen()
        }
    }
}