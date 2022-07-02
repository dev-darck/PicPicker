package com.project.bottom_navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.project.navigationapi.config.Config

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addDestinations(navController: NavController, screens: Sequence<Config>) {
    screens.forEach { destination ->
        composable(
            destination.route.routeScheme,
            destination.arguments,
            destination.deepLinks,
        ) {
            if (navController.currentDestination?.route == destination.route.routeScheme) {
                key(destination.route.routeScheme) {
                    val parentEntry = remember(it) {
                        it
                    }
                    destination.openScreen(this, parentEntry)
                }
            }
        }
    }
}