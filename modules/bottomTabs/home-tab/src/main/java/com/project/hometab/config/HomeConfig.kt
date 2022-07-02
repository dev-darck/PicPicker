package com.project.hometab.config

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.project.common_resources.R
import com.project.hometab.screen.HomeScreen
import com.project.hometab.viewmodule.HomeViewModel
import com.project.navigationapi.config.*
import com.project.navigationapi.navigation.Navigation
import javax.inject.Inject

class HomeConfig @Inject constructor(
    private val navigation: Navigation,
) : BottomConfig, ToolBarConfig {
    override val icon: Int = R.drawable.home_tab
    override val order: Int = 0
    override val route: Route = HomeRoute
    override val label: Int = R.string.home_tab
    override val isRoot: Boolean = true
    override val leftBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.settings_icon
        contentDescription = R.string.default_content_descriptions
        click = {
            navigation.navigate(SettingsRoute.createRoute("home"))
        }
    }
    override val rightBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.shearch_icon
        contentDescription = R.string.default_content_descriptions
        click = {
            navigation.navigate(SearchScreenRoute.createRoute())
        }
    }

    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        val viewModel = hiltViewModel<HomeViewModel>(it)
        HomeScreen(viewModel)
    }
}