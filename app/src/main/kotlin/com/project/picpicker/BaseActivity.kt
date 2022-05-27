package com.project.picpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.project.common_compos_ui.theme.AppTheme
import com.project.common_compos_ui.theme.StatusBarColorProvider
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.BottomSheetConfig
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.ToolBarConfig
import com.project.navigationapi.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {

    @set:Inject
    var appNavigation: Navigation? = null

    @set:Inject
    var screens: Set<@JvmSuppressWildcards Config>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screens = screens?.asSequence() ?: emptySequence()
        val bottomScreen = screens.convertTo<BottomConfig>().sortedBy(BottomConfig::order)
        val bottomSheetConfig = screens.convertTo<BottomSheetConfig>()
        val startDestination = bottomScreen.find(BottomConfig::isRoot)?.route?.routeScheme.orEmpty()
        val toolBarConfig: Sequence<ToolBarConfig> = screens.convertTo()
        setContent {
            AppTheme {
                StatusBarColorProvider()
                if (startDestination.isNotEmpty() && appNavigation != null) {
                    PicPikerScaffold(
                        appNavigation = appNavigation!!,
                        screens,
                        bottomScreen,
                        toolBarConfig,
                        bottomSheetConfig,
                        startDestination
                    )
                } else {
                    //Ошибочное состояние ввывести ошибку!
                }
            }
        }
    }
}