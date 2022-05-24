package com.project.hometab.config

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.project.common_resources.R
import com.project.hometab.screen.HomeScreen
import com.project.navigationapi.config.*
import com.project.navigationapi.navigation.Navigation
import javax.inject.Inject

class HomeConfig @Inject constructor(
    private val navigation: Navigation,
) : BottomConfig, ToolBarConfig {
    override val icon: Int = R.drawable.home_tab
    override val order: Int = 0
    override val route: Route = HomeRoute
    override val lable: Int = 0
    override val isRoot: Boolean = true
    override val leftBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.settings_icon
        contentDescription = R.string.default_content_descriptions
        click = {
        }
    }
    override val rightBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.shearch_icon
        contentDescription = R.string.default_content_descriptions
        click = {
        }
    }
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(HomeRoute.scheme) {
            type = NavType.StringType
            defaultValue = HomeRoute.tabVariant.first
        }
    )
    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        HomeScreen()
    }
}