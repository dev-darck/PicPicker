package com.project.picpicker

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.project.bottom_navigation.BottomNavigation
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.BottomSheetConfig
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.ToolBarConfig
import com.project.navigationapi.navigation.Directions
import com.project.navigationapi.navigation.NavigateUp
import com.project.navigationapi.navigation.Navigation
import com.project.navigationapi.navigation.PopBackStack
import com.project.picpicker.graph.addBottomSheetDestination
import com.project.picpicker.graph.addDestinations
import com.project.toolbar.Toolbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun PicPikerScaffold(
    appNavigation: Navigation,
    config: Sequence<Config> = emptySequence(),
    bottomConfig: Sequence<BottomConfig> = emptySequence(),
    toolBarConfig: Sequence<ToolBarConfig> = emptySequence(),
    bottomSheetConfig: Sequence<BottomSheetConfig> = emptySequence(),
    startDestination: String = "",
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

        val sheetState = rememberModalBottomSheetState(
            ModalBottomSheetValue.Hidden,
            SwipeableDefaults.AnimationSpec
        )
        val bottomSheetNavigator = rememberBottomSheetNavigatorFix(sheetState)
        navController.navigatorProvider += bottomSheetNavigator

        ModalBottomSheetLayout(
            modifier = Modifier,
            sheetElevation = 0.dp,
            scrimColor = Color.Transparent,
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
        ) {
            Scaffold(
                modifier = Modifier,
                topBar = {
                    Toolbar(
                        navController = navController,
                        toolbarConfigs = toolBarConfig
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
                    enterTransition = { fadeIn(animationSpec = tween(300)) },
                    exitTransition = { fadeOut(animationSpec = tween(300)) },
                ) {
                    addDestinations(navController, config)
                    addBottomSheetDestination(navController, bottomSheetConfig)
                }
            }
        }
    }
}

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun rememberBottomSheetNavigatorFix(
    sheetState: ModalBottomSheetState,
): BottomSheetNavigator {
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}