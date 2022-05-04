package com.project.bottom_navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.navigation.navigation.hideBottomNavigation

private val BottomNavigationItemHorizontalPadding = 10.dp

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
                    CustomTab(bottomEntry, animationProgress)
                }
            )
        }
    }
}

@Composable
fun CustomTab(bottomEntry: BottomNavigationUi, animationProgress: Float) {
    val color = MaterialTheme.colors.secondary
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = BottomNavigationItemHorizontalPadding),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = bottomEntry.icon),
            contentDescription = bottomEntry.screen.route,
            modifier = Modifier
                .fillMaxWidth()
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
                radius = size.minDimension / 10
            )
        }
    }
}