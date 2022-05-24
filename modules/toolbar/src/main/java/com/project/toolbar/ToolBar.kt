package com.project.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.common_resources.R
import com.project.common_ui.tab.CustomTab
import com.project.common_ui.tab.SizeProportion
import com.project.common_ui.tab.TabIconWitchPoint
import com.project.navigationapi.config.BottomIcon
import com.project.navigationapi.config.DefaultRoute
import com.project.navigationapi.config.HomeRoute
import com.project.navigationapi.config.HomeRoute.tabVariant
import com.project.navigationapi.config.Route
import com.project.navigationapi.config.ToolBarConfig
import com.project.navigationapi.config.root

private val TOOLBAR_HORIZONTAL_PADDING = 30.dp
private val Elevation = 15.dp
private val SizeIcon = 24.dp
private val SizeNavigationIcon = 20.dp
private val ToolbarSize = 50.dp

@Composable
fun Toolbar(
    navController: NavController,
    toolbarConfigs: Sequence<ToolBarConfig>,
    toolbarStateManager: ToolbarStateManager
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    toolbarConfigs.firstOrNull { screen -> screen.root == currentRoute }
        ?.let {
            ToolbarSurface(
                toolbarConfig = it,
                navController = navController,
                toolbarStateManager = toolbarStateManager
            )
        }
}

@Composable
private fun ToolbarSurface(
    navController: NavController? = null,
    toolbarConfig: ToolBarConfig,
    toolbarStateManager: ToolbarStateManager? = null
) {
    Surface(
        modifier = Modifier
            .height(ToolbarSize)
            .background(colors.surface)
            .shadow(
                Elevation + LocalAbsoluteElevation.current,
                RectangleShape,
                false,
                colors.secondaryVariant
            ),
    ) {
        if (toolbarConfig.route.routeScheme == HomeRoute.routeScheme) {
            HomeToolbar(
                config = toolbarConfig,
                toolbarStateManager = toolbarStateManager
            )
        } else {
            Toolbar(toolbarConfig)
        }
    }
}

@Composable
private fun HomeToolbar(
    config: ToolBarConfig,
    toolbarStateManager: ToolbarStateManager?
) {
    Toolbar(config = config) { modifier ->
        HomeIcon(modifier) { tab ->
            toolbarStateManager?.setState(
                if (tab == "List") ToolbarState.List else ToolbarState.Grid
            )
        }
    }
}

@Composable
private fun HomeIcon(modifier: Modifier, click: (String) -> Unit = { }) {
    val tabs = tabVariant.toList()
    var currentTab by remember { mutableStateOf(tabs[0]) }
    CustomTab(modifier) {
        tabs.forEach { tab ->
            CustomTab(
                selected = tab == currentTab,
                alwaysShowLabel = false,
                onClick = {
                    currentTab = tab
                    click(currentTab)
                }, customTab = {
                    TabIconWitchPoint(
                        contentDescription = stringResource(
                            id = if (tab == tabVariant.second) {
                                R.string.grid_tab
                            } else {
                                R.string.list_tab
                            }
                        ),
                        imageVector = ImageVector.vectorResource(
                            id = if (tab == tabVariant.second) {
                                R.drawable.grid_icon
                            } else {
                                R.drawable.list_icon
                            }
                        ),
                        modifier = Modifier
                            .width(SizeIcon + 20.dp)
                            .padding(top = 13.dp),
                        sizeIcon = SizeIcon,
                        animationProgress = it,
                        sizeProportion = SizeProportion.MIDDLE,
                        color = colors.onPrimary
                    )
                },
                selectedContentColor = colors.onPrimary,
                unselectedContentColor = colors.onSecondary
            )
        }
    }
}

@Composable
private fun Toolbar(
    config: ToolBarConfig,
    content: @Composable (RowScope.(Modifier) -> Unit?)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        if (config.leftBottom != null) {
            Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING))
            ToolbarBottom(config.leftBottom!!, Modifier)
        } else {
            Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING + SizeNavigationIcon))
        }

        if (content == null) {
            Text(
                text = stringResource(id = config.lable),
                style = MaterialTheme.typography.h6,
                color = colors.onBackground,
                modifier = Modifier.weight(1F, true),
                textAlign = TextAlign.Center
            )
        } else {
            content(
                Modifier
                    .weight(1F, true)
                    .padding(start = SizeIcon, end = SizeIcon)
            )
        }

        if (config.rightBottom != null) {
            ToolbarBottom(config.rightBottom!!, Modifier)
            Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING))
        } else {
            Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING + SizeNavigationIcon))
        }
    }
}

@Composable
private fun ToolbarBottom(config: BottomIcon, modifier: Modifier = Modifier) {
    IconButton(config.click, modifier.size(SizeNavigationIcon)) {
        val icon = config.icon ?: return@IconButton
        val content = config.contentDescription ?: R.string.default_content_descriptions
        Icon(
            tint = colors.onSecondary,
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = stringResource(id = content),
        )
    }
}

@Composable
@Preview
private fun PreviewToolBar() {
    ToolbarSurface(toolbarConfig = object : ToolBarConfig {
        override val leftBottom: BottomIcon = BottomIcon().apply {
            icon = R.drawable.settings_icon
        }
        override val rightBottom: BottomIcon = BottomIcon().apply {
            icon = R.drawable.settings_icon
        }
        override val route: Route = DefaultRoute
        override val lable: Int = R.string.preview_name
    })
}

@Composable
@Preview
private fun PreviewHomeToolBar() {
    ToolbarSurface(toolbarConfig = object : ToolBarConfig {
        override val leftBottom: BottomIcon = BottomIcon().apply {
            icon = R.drawable.settings_icon
        }
        override val rightBottom: BottomIcon = BottomIcon().apply {
            icon = R.drawable.settings_icon
        }
        override val route: Route = HomeRoute
        override val lable: Int = R.string.preview_name
    })
}