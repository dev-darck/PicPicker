package com.project.navigationapi.config

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

interface Config {
    val route: Route
    val arguments: List<NamedNavArgument>
        get() = emptyList()
    val deepLinks: List<NavDeepLink>
        get() = emptyList()
    val openScreen: @Composable () -> Unit
}