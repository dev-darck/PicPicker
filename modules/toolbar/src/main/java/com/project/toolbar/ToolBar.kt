package com.project.toolbar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
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
import com.project.navigationapi.config.BottomIcon
import com.project.navigationapi.config.DefaultRoute
import com.project.navigationapi.config.HomeRoute
import com.project.navigationapi.config.Route
import com.project.navigationapi.config.ToolBarConfig
import com.project.navigationapi.config.root

private val TOOLBAR_HORIZONTAL_PADDING = 30.dp
private val Elevation = 15.dp
private val SizeIcon = 24.dp
private val BottomNavigationHeight = 50.dp

@Composable
fun Toolbar(navController: NavController, toolbarConfigs: Sequence<ToolBarConfig>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    toolbarConfigs.firstOrNull { screen -> screen.root == currentRoute }
        ?.let { ToolbarSurface(toolbarConfig = it, navController = navController) }
}

@Composable
private fun ToolbarSurface(navController: NavController? = null, toolbarConfig: ToolBarConfig) {
    Surface(
        modifier = Modifier
            .height(BottomNavigationHeight)
            .background(MaterialTheme.colors.surface)
            .shadow(
                Elevation + LocalAbsoluteElevation.current,
                RectangleShape,
                false,
                MaterialTheme.colors.secondaryVariant
            ),
    ) {
        if (toolbarConfig.route.routeScheme == HomeRoute.routeScheme) {
            HomeToolbar(navController = navController, config = toolbarConfig)
        } else {
            Toolbar(toolbarConfig)
        }
    }
}

@Composable
private fun HomeToolbar(navController: NavController? = null, config: ToolBarConfig) {
    Toolbar(config = config) { modifier ->
        HomeIcon(modifier) { tab ->
            navController?.navigate(HomeRoute.createRoute(tab))
        }
    }
}

@Composable
private fun HomeIcon(modifier: Modifier, click: (String) -> Unit = { }) {
    val tabs = HomeRoute.tabVariant.toList()
    var currentTab by remember { mutableStateOf(tabs[0]) }
    Tab(modifier) {
        tabs.forEach { tab ->
            CustomTab(
                selected = tab == currentTab,
                alwaysShowLabel = false,
                onClick = {
                    currentTab = tab
                    click(currentTab)
                }, customTab = {
                    TabIcon(animationProgress = it, tab = tab)
                })
        }
    }
}

@Composable
private fun TabIcon(animationProgress: Float, tab: String) {
    val color = MaterialTheme.colors.onPrimary
    Column(
        modifier = Modifier
            .width(SizeIcon + 20.dp)
            .padding(top = 13.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = if (tab == HomeRoute.tabVariant.second) {
                R.drawable.grid_icon
            } else {
                R.drawable.list_icon
            }),
            contentDescription = stringResource(id = if (tab == HomeRoute.tabVariant.second) {
                R.string.grid_tab
            } else {
                R.string.list_tab
            }),
            modifier = Modifier
                .size(SizeIcon)
                .align(CenterHorizontally)
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .alpha(animationProgress)
                .align(CenterHorizontally)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(
                color = color,
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 5
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
        Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING))

        if (config.leftBottom != null) {
            ToolBarBottom(config.leftBottom!!, Modifier)
        }

        if (content == null) {
            Text(
                text = stringResource(id = config.lable),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.weight(2F, true),
                textAlign = TextAlign.Center
            )
        } else {
            content(Modifier.weight(1F, true))
        }

        if (config.rightBottom != null) {
            ToolBarBottom(config.rightBottom!!, Modifier)
        }

        Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING))
    }
}

@Composable
private fun ToolBarBottom(config: BottomIcon, modifier: Modifier = Modifier) {
    IconButton(config.click) {
        val icon = config.icon ?: return@IconButton
        val content = config.contentDescription ?: R.string.default_content_descriptions
        Icon(
            modifier = modifier,
            tint = MaterialTheme.colors.secondary,
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
            icon = R.drawable.preview_icon
        }
        override val rightBottom: BottomIcon = BottomIcon().apply {
            icon = R.drawable.preview_icon
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
            icon = R.drawable.preview_icon
        }
        override val rightBottom: BottomIcon = BottomIcon().apply {
            icon = R.drawable.preview_icon
        }
        override val route: Route = HomeRoute
        override val lable: Int = R.string.preview_name
    })
}