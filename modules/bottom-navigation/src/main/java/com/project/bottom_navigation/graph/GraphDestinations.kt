package com.project.bottom_navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.project.navigation.navigation.NavigationDestination

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addComposableDestinations(screens: Set<NavigationDestination>) {
    screens.forEach { destination ->
        composable(destination.route(), destination.arguments, destination.deepLinks) {
            destination.openScreen()
        }
    }
}