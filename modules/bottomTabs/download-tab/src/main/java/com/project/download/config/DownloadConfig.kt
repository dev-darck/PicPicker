package com.project.download.config

import androidx.compose.runtime.Composable
import com.project.bottom_navigation.BottomNavigationEntry
import com.project.bottom_navigation.BottomNavigationUi
import com.project.configuration.ConfigurationTab.download
import com.project.download.R
import com.project.download.screen.Download
import javax.inject.Inject

private object DownloadTab : BottomNavigationEntry(download)

class DownloadConfig @Inject constructor(): BottomNavigationUi {
    override val screen: BottomNavigationEntry = DownloadTab
    override val icon: Int = R.drawable.download_tab
    override val order: Int = 2
    override val openScreen: @Composable () -> Unit = {
        Download()
    }
}