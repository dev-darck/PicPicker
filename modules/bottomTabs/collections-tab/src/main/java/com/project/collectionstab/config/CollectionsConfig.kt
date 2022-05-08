package com.project.collectionstab.config

import androidx.compose.runtime.Composable
import com.project.collectionstab.screen.Collections
import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.CollectionsRoute
import com.project.navigationapi.config.Route
import javax.inject.Inject
import com.project.collectionstab.R as Res

class CollectionsConfig @Inject constructor(): BottomConfig {
    override val route: Route = CollectionsRoute
    override val icon: Int = Res.drawable.collections_tab
    override val order: Int = 1
    override val openScreen: @Composable () -> Unit = {
        Collections()
    }
}