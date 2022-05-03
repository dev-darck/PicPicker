package com.project.bottom_navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.navigation.navigation.hideBottomNavigation


private val BottomNavigationItemHorizontalPadding = 10.dp
private val SpacingIcon = 5.dp

@Composable
fun BottomNavigation(navController: NavController, bottomScreens: Set<BottomNavigationUi>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val hideBottomNav by derivedStateOf { navBackStackEntry.hideBottomNavigation }

    val size = if (hideBottomNav) {
        Modifier.size(animateDpAsState(targetValue = 0.dp, animationSpec = tween()).value)
    } else {
        Modifier
    }

    CustomBottomNavigation(
        modifier = size
    ) {
        bottomScreens.forEach { bottomEntry ->
            BottomTab(
                selected = currentRoute == bottomEntry.screen.route,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(bottomEntry.screen.route) {
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                customTab = { animationProgress ->
                    Column(
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(top = 10.dp)
                    ) {
                        Box(Modifier.padding(bottom = BottomNavigationItemHorizontalPadding)) {
                            Icon(
                                imageVector = bottomEntry.icon,
                                contentDescription = bottomEntry.screen.route,
                            )
                        }
                        Box(
                            Modifier
                                .alpha(animationProgress)
                                .padding(horizontal = BottomNavigationItemHorizontalPadding)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Circle,
                                contentDescription = bottomEntry.screen.route,
                                modifier = Modifier
                                    .size(SpacingIcon, SpacingIcon)
                                    .alpha(animationProgress)
                            )
                        }
                    }
                }
            )
        }
    }
}