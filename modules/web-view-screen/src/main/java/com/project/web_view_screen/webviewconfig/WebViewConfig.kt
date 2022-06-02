package com.project.web_view_screen.webviewconfig

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.project.common_resources.R
import com.project.navigationapi.config.*
import com.project.navigationapi.navigation.Navigation
import com.project.web_view_screen.screen.WebView
import com.project.web_view_screen.viewmodel.WebViewViewModel
import javax.inject.Inject

class WebViewConfig @Inject constructor(
    navigation: Navigation,
) : Config, ToolBarConfig {
    override val route: Route = WebViewRoute
    override val lable: Int = R.string.add_collections
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(WebViewRoute.schemeUrl) { type = NavType.StringType },
        navArgument(WebViewRoute.schemeType) { type = NavType.StringType },
    )
    override val leftBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.back_icon
        contentDescription = R.string.default_content_descriptions
        click = {
            navigation.popBackStack()
        }
    }

    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        val viewModel: WebViewViewModel = hiltViewModel(it)
        WebView(viewModel = viewModel)
    }
}