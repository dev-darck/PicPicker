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
import com.project.bottom_navigation.graph.addDestinations
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.ToolBarConfig
import com.project.navigationapi.navigation.Directions
import com.project.navigationapi.navigation.NavigateUp
import com.project.navigationapi.navigation.Navigation
import com.project.navigationapi.navigation.PopBackStack
import com.project.toolbar.Toolbar
import com.project.toolbar.ToolbarStateManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PicPikerScaffold(
    appNavigation: Navigation,
    config: Sequence<Config> = emptySequence(),
    bottomConfig: Sequence<BottomConfig> = emptySequence(),
    toolBarConfig: Sequence<ToolBarConfig> = emptySequence(),
    startDestination: String = "",
    toolbarStateManager: ToolbarStateManager,
) {
    Surface {
        val navController = rememberAnimatedNavController()

        LaunchedEffect(navController) {
            appNavigation.destinations.onEach { event ->
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
            }.collect()
        }

        Scaffold(
            modifier = Modifier,
            topBar = {
                Toolbar(
                    navController = navController,
                    toolbarConfigs = toolBarConfig,
                    toolbarStateManager = toolbarStateManager
                )
            },
            bottomBar = {
                BottomNavigation(
                    navController = navController,
                    bottomConfig
                )
            }
        ) { paddingValues ->
            AnimatedNavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = startDestination,
                enterTransition = { fadeIn(animationSpec = tween(0)) },
                exitTransition = { fadeOut(animationSpec = tween(0)) },
            ) {
                addDestinations(navController, config)
            }
        }
    }
}