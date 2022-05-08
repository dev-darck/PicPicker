package com.project.download.config

import androidx.compose.runtime.Composable
import com.project.download.screen.Download
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.DownloadRoute
import com.project.navigationapi.config.Route
import javax.inject.Inject
import com.project.download.R as Res

class DownloadConfig @Inject constructor(): BottomConfig {
    override val route: Route = DownloadRoute
    override val icon: Int = Res.drawable.download_tab
    override val order: Int = 2
    override val openScreen: @Composable () -> Unit = {
        Download()
    }
}