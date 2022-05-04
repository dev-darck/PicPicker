package com.project.picpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.project.bottom_navigation.BottomNavigationUi
import com.project.common_compos_ui.theme.AppTheme
import com.project.common_compos_ui.theme.StatusBarColorProvider
import com.project.navigation.navigation.Navigation
import com.project.navigation.navigation.NavigationDestination
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigation: Navigation

    //Сделать реализвацию экранов
//    @set:Inject
    var screens: Set<@JvmSuppressWildcards(true) NavigationDestination>? = null

    @set:Inject
    var bottomScreens: Set<@JvmSuppressWildcards(true) BottomNavigationUi>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomScreens = bottomScreens?.sortedBy(BottomNavigationUi::order)?.toSet()
        setContent {
            AppTheme {
                StatusBarColorProvider()
                val screensApp = screens ?: emptySet()
                val bottomTabs = bottomScreens ?: return@AppTheme
                PicPikerScaffold(appNavigation = appNavigation, screensApp, bottomTabs)
            }
        }
    }
}