package com.project.toolbar

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.common_resources.R
import com.project.navigationapi.config.*

private val TOOLBAR_HORIZONTAL_PADDING = 30.dp
private val PADDING_TEXT = 15.dp
private val Elevation = 15.dp
private val SizeNavigationIcon = 20.dp
private val ToolbarSize = 50.dp

@Composable
fun Toolbar(
    navController: NavController,
    toolbarConfigs: Sequence<ToolBarConfig>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    if (navBackStackEntry == null) return
    val currentRoute = navBackStackEntry?.destination?.route
    toolbarConfigs.firstOrNull { screen -> screen.root == currentRoute }
        ?.let {
            ToolbarSurface(toolbarConfig = it, navBackStackEntry!!.arguments)
        }
}

@Composable
private fun ToolbarSurface(
    toolbarConfig: ToolBarConfig,
    bundle: Bundle?
) {
    val background = if (toolbarConfig.isTransparentBackground) Color.Transparent else colors.surface
    val name = bundle?.getString(Route.name, "").orEmpty().replace(DELIMITER, DELIMITER_NAVIGATION)
    Surface(
        modifier = Modifier.statusBarsPadding()
            .height(ToolbarSize)
            .background(background)
            .shadow(
                Elevation + LocalAbsoluteElevation.current,
                RectangleShape,
                false,
                colors.secondaryVariant
            ),
    ) {
        Toolbar(toolbarConfig, name)
    }
}

@Composable
private fun Toolbar(config: ToolBarConfig, name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        if (config.leftBottom != null) {
            Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING))
            ToolbarBottom(config.leftBottom!!, Modifier)
            Spacer(Modifier.size(PADDING_TEXT))
        } else {
            Spacer(Modifier.size(TOOLBAR_HORIZONTAL_PADDING + SizeNavigationIcon))
        }

        val label = config.label
        Text(
            text = label?.let { stringResource(id = label) } ?: name,
            style = MaterialTheme.typography.h1,
            color = colors.onBackground,
            maxLines = 1,
            modifier = Modifier.weight(1F, true),
            textAlign = TextAlign.Center
        )

        if (config.rightBottom != null) {
            Spacer(Modifier.size(PADDING_TEXT))
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
        override val label: Int = R.string.preview_name
    }, bundleOf())
}