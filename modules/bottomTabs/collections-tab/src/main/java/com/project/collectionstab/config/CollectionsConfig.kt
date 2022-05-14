package com.project.collectionstab.config

import androidx.compose.runtime.Composable
import com.project.collectionstab.screen.Collections
import com.project.common_resources.R
import com.project.navigationapi.config.*
import com.project.navigationapi.navigation.Navigation
import javax.inject.Inject

class CollectionsConfig @Inject constructor(
    val navigation: Navigation,
) : BottomConfig, ToolBarConfig {
    override val route: Route = CollectionsRoute
    override val lable: Int = R.string.collections_label
    override val icon: Int = R.drawable.collections_tab
    override val rightBottom: BottomIcon = BottomIcon().apply {
        icon = R.drawable.shearch_icon
        contentDescription = R.string.default_content_descriptions
        click = {

        }
    }
    override val order: Int = 1
    override val openScreen: @Composable () -> Unit = {
        Collections()
    }
}