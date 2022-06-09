package com.project.profile.config

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.project.common_resources.R
import com.project.navigationapi.config.*
import com.project.profile.screen.Profile
import javax.inject.Inject

class ProfileConfig @Inject constructor() : BottomConfig, ToolBarConfig {
    override val route: Route = ProfileRoute
    override val label: Int = R.string.profile_label
    override val icon: Int = R.drawable.profile_tab
    override val order: Int = 3
    override val leftBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.settings_icon
        contentDescription = R.string.default_content_descriptions
        click = {
        }
    }
    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        Profile()
    }
}