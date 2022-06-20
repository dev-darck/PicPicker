package com.project.settings_app.settingconfig

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.Route
import com.project.navigationapi.config.SettingsRoute
import com.project.settings_app.screen.Settings
import javax.inject.Inject

class SettingConfig @Inject constructor() : Config {
    override val route: Route = SettingsRoute
    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        Settings()
    }
}