package com.project.picpicker

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.project.bottom_navigation.BottomNavigation
import com.project.bottom_navigation.BottomNavigationUi
import com.project.bottom_navigation.graph.addBottomNavigationDestinations
import com.project.bottom_navigation.graph.addComposableDestinations
import com.project.navigation.navigation.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PicPikerScaffold(
    appNavigation: Navigation,
    screens: Set<NavigationDestination> = emptySet(),
    bottomTab: Set<BottomNavigationUi> = emptySet()
) {
    Surface {
        val navController = rememberAnimatedNavController()

        LaunchedEffect(navController) {
            appNavigation.destinations.collect { event ->
                when (event) {
                    is NavigateUp -> {
                        navController.navigateUp()
                    }
                    is Directions -> navController.navigate(
                        event.destination,
                        event.builder
                    )
                    is PopBackStack -> {
                        navController.popBackStack()
                    }
                }
            }
        }

        Scaffold(
            modifier = Modifier,
            bottomBar = {
                BottomNavigation(
                    navController = navController,
                    bottomTab
                )
            }
        ) { paddingValues ->
            AnimatedNavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = bottomTab.find { it.isRoot }?.screen?.route.orEmpty(),
                enterTransition = { fadeIn(animationSpec = tween(0)) },
                exitTransition = { fadeOut(animationSpec = tween(0)) },
            ) {
                addComposableDestinations(screens)
                addBottomNavigationDestinations(bottomTab)
            }
        }
    }
}