package com.project.profile.config

import androidx.compose.runtime.Composable
import com.project.bottom_navigation.BottomNavigationEntry
import com.project.bottom_navigation.BottomNavigationUi
import com.project.configuration.ConfigurationTab.profile
import com.project.profile.R
import com.project.profile.screen.Profile
import javax.inject.Inject

private object ProfileTab : BottomNavigationEntry(profile)

class ProfileConfig @Inject constructor() : BottomNavigationUi {
    override val screen: BottomNavigationEntry = ProfileTab
    override val icon: Int = R.drawable.profile_tab
    override val order: Int = 3
    override val openScreen: @Composable () -> Unit = {
        Profile()
    }
}