package com.project.bottom_navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.project.navigationapi.config.Config

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addDestinations(screens: Sequence<Config>) {
    screens.forEach { destination ->
        composable(destination.route.routeScheme, destination.arguments, destination.deepLinks) {
            destination.openScreen()
        }
    }
}