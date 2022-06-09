package com.project.picpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.project.basenavigation.manager.NavigationManager
import com.project.common_compos_ui.theme.AppTheme
import com.project.common_compos_ui.theme.StatusBarColorProvider
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.ToolBarConfig
import com.project.navigationapi.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {

    @set:Inject
    lateinit var appNavigation: Navigation

    @set:Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screens = navigationManager.lisScreens()
        val bottomScreen = navigationManager.listBottomScreen()
        val startDestination = navigationManager.startDestination()
        val toolBarConfig = navigationManager.listToolbar()
        setContent {
            AppTheme {
                StatusBarColorProvider()
                if (startDestination.isNotEmpty()) {
                    PicPikerScaffold(
                        appNavigation,
                        screens, bottomScreen,
                        toolBarConfig, startDestination,
                    )
                } else {
                    //Ошибочное состояние ввывести ошибку!
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationManager.deleteScreens()
    }
}