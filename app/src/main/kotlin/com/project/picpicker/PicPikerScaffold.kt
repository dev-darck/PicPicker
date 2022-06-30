package com.project.picpicker

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
) {
    val navController = rememberAnimatedNavController()
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(navController) {
        appNavigation.destinations.onEach { event ->
            when (event) {
                is NavigateUp -> {
                    navController.navigateUp()
                }

                is Directions -> navController.navigate(
                    event.destination,
                    event.builder,
                )

                is PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }.collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Toolbar(
                navController = navController,
                toolbarConfigs = toolBarConfig,
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
            contentAlignment = Alignment.Center,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) {
            addDestinations(navController, config)
        }
    }
}