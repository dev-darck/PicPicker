package com.project.picpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
            //Придумать экран с ошибкой и в случае если нет ни одного экрана показать ползователю ошибку
            val screensApp = screens ?: return@setContent
            val bottomTabs = bottomScreens ?: return@setContent
            PicPikerScaffold(appNavigation = appNavigation, screensApp, bottomTabs)
        }
    }
}