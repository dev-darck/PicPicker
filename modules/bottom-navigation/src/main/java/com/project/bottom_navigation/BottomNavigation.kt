package com.project.bottom_navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.common_ui.tab.CustomTab
import com.project.common_ui.tab.TabIconWitchPoint
import com.project.navigationapi.config.BottomConfig

private val BottomNavigationItemHorizontalPadding = 10.dp
private val SizeIcon = 20.dp
private val Elevation = 15.dp

@Composable
fun BottomNavigation(navController: NavController, bottomScreens: Sequence<BottomConfig>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val hideBottomNav = false

    val size = if (hideBottomNav) {
        Modifier.size(animateDpAsState(targetValue = 0.dp, animationSpec = tween()).value)
    } else {
        Modifier
    }

    CustomBottomNavigation(
        modifier = size,
        elevation = Elevation
    ) {
        bottomScreens.forEach { bottomEntry ->
            CustomTab(
                modifier = Modifier.weight(1F),
                selected = currentRoute == bottomEntry.route.routeScheme,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(bottomEntry.route.routeScheme) {
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                customTab = { animationProgress ->
                    TabIconWitchPoint(
                        animationProgress = animationProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = BottomNavigationItemHorizontalPadding),
                        imageVector = ImageVector.vectorResource(id = bottomEntry.icon),
                        sizeIcon = SizeIcon,
                        contentDescription = bottomEntry.route.routeScheme
                    )
                },
                selectedContentColor = colors.secondary,
                unselectedContentColor = colors.onSecondary,
            )
        }
    }
}