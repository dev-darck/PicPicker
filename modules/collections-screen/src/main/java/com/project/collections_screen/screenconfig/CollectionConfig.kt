package com.project.collections_screen.screenconfig;

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.project.collections_screen.screen.CollectionScreen
import com.project.collections_screen.viewmodel.CollectionViewModel
import com.project.common_resources.R
import com.project.navigationapi.config.*
import com.project.navigationapi.navigation.Navigation
import javax.inject.Inject

class CollectionConfig @Inject constructor(
    navigation: Navigation
) : Config, ToolBarConfig {
    override val route: Route = CollectionScreenRout
    override val leftBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.back_icon
        contentDescription = R.string.default_content_descriptions
        click = {
            navigation.popBackStack()
        }
    }
    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        val viewModel = hiltViewModel<CollectionViewModel>(it)
        CollectionScreen(viewModel)
    }
}
