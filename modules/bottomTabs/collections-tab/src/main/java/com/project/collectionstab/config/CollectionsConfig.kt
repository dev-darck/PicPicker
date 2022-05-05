package com.project.collectionstab.config

import androidx.compose.runtime.Composable
import com.project.bottom_navigation.BottomNavigationEntry
import com.project.bottom_navigation.BottomNavigationUi
import com.project.collectionstab.R
import com.project.collectionstab.screen.Collections
import com.project.configuration.ConfigurationTab.collections
import javax.inject.Inject

private object CollectionsTab : BottomNavigationEntry(collections)

class CollectionsConfig @Inject constructor(): BottomNavigationUi {
    override val screen: BottomNavigationEntry = CollectionsTab
    override val icon: Int = R.drawable.collections_tab
    override val order: Int = 1
    override val openScreen: @Composable () -> Unit = {
        Collections()
    }
}