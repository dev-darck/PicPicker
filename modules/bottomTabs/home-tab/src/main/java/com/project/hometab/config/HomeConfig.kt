package com.project.hometab.config

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.project.hometab.screen.Home
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.HomeRoute
import com.project.navigationapi.config.Route
import com.project.navigationapi.config.ToolBarConfig
import javax.inject.Inject
import com.project.common_resources.R

class HomeConfig @Inject constructor() : BottomConfig, ToolBarConfig {
    override val icon: Int = R.drawable.home_tab
    override val order: Int = 0
    override val route: Route = HomeRoute
    override val lable: Int = 0
    override val isRoot: Boolean = true
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(HomeRoute.scheme) {
            type = NavType.StringType
            defaultValue = HomeRoute.tabVariant.first
        }
    )
    override val openScreen: @Composable () -> Unit = {
        Home()
    }
}