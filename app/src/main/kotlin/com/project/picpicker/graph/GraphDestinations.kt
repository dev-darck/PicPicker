package com.project.picpicker.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.project.navigationapi.config.BottomSheetConfig
import com.project.navigationapi.config.Config

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addDestinations(navController: NavController, screens: Sequence<Config>) {
    screens.forEach { destination ->
        composable(destination.route.routeScheme, destination.arguments, destination.deepLinks) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(destination.route.routeScheme)
            }
            destination.openScreen?.let { openScreen -> openScreen(this, parentEntry) }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.addBottomSheetDestination(
    navController: NavController,
    screens: Sequence<BottomSheetConfig>,
) {
    screens.forEach { destination ->
        bottomSheet(destination.route.routeScheme, destination.arguments, destination.deepLinks) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(destination.route.routeScheme)
            }
            destination.openBottomSheet(this, parentEntry)
        }
    }
}