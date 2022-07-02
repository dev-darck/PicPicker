package com.project.searchsreen.searchconfig

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.project.common_resources.R
import com.project.navigationapi.config.*
import com.project.navigationapi.navigation.Navigation
import com.project.searchsreen.screen.Search
import javax.inject.Inject

class SearchConfig @Inject constructor(
    navigation: Navigation,
) : Config, ToolBarConfig {
    override val route: Route = SearchScreenRoute
    override val label: Int = R.string.search
    override val leftBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.back_icon
        contentDescription = R.string.default_content_descriptions
        click = {
            navigation.popBackStack()
        }
    }
    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        Search()
    }
}