package com.project.hometab.config

import androidx.compose.runtime.Composable
import com.project.bottom_navigation.BottomNavigationEntry
import com.project.bottom_navigation.BottomNavigationUi
import com.project.configuration.ConfigurationTab.home
import com.project.hometab.R
import com.project.hometab.screen.Home
import javax.inject.Inject

private object HomeTab : BottomNavigationEntry(home)

class HomeConfig @Inject constructor() : BottomNavigationUi {
    override val screen: BottomNavigationEntry = HomeTab
    override val icon: Int = R.drawable.home_tab
    override val order: Int = 0
    override val isRoot: Boolean = true
    override val openScreen: @Composable () -> Unit = {
        Home()
    }
}