package com.project.download.config

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.project.common_resources.R
import com.project.download.screen.Download
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.DownloadRoute
import com.project.navigationapi.config.Route
import com.project.navigationapi.config.ToolBarConfig
import javax.inject.Inject

class DownloadConfig @Inject constructor(): BottomConfig, ToolBarConfig {
    override val route: Route = DownloadRoute
    override val lable: Int = R.string.download_label
    override val icon: Int = R.drawable.download_tab
    override val order: Int = 2
    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        Download()
    }
}