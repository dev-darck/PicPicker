package com.project.profile.config

import androidx.compose.runtime.Composable
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.ProfileRoute
import com.project.navigationapi.config.Route
import com.project.profile.screen.Profile
import javax.inject.Inject
import com.project.profile.R as Res

class ProfileConfig @Inject constructor() : BottomConfig {
    override val route: Route = ProfileRoute
    override val icon: Int = Res.drawable.profile_tab
    override val order: Int = 3
    override val openScreen: @Composable () -> Unit = {
        Profile()
    }
}