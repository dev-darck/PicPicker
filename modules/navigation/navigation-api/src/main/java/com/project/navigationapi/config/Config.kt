package com.project.navigationapi.config

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink

interface Config {
    val route: Route
    val arguments: List<NamedNavArgument>
        get() = emptyList()
    val deepLinks: List<NavDeepLink>
        get() = emptyList()
    val openScreen: @Composable (AnimatedVisibilityScope.(NavBackStackEntry) -> Unit)?
}

val <T: Config> T.root get() = this.route.routeScheme