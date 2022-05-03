package com.project.picpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.project.common_compos_ui.theme.AppTheme
import com.project.common_compos_ui.theme.StatusBarColorProvider
import com.project.navigation.PicPikerScaffold
import com.project.navigation.bottomnav.BottomNavigationUi
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

    //Сделать реализвацию базовых экранов
//    @set:Inject
    var bottomScreens: Set<@JvmSuppressWildcards(true) BottomNavigationUi>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomScreens = bottomScreens?.sortedBy { it.order }?.toSet()
        setContent {
            AppTheme {
                StatusBarColorProvider()
                val screensApp = screens ?: emptySet()
                val bottomTabs = bottomScreens ?: return@AppTheme
                //Придумать экран с ошибкой и в случае если нет ни одного экрана показать ползователю ошибку
                PicPikerScaffold(appNavigation = appNavigation, screensApp, bottomTabs)
            }
        }
    }
}