package com.project.navigation.navigation

import androidx.navigation.NavOptionsBuilder

sealed class NavigationEvent
object NavigateUp : NavigationEvent()
class Directions(
    val destination: String,
    val builder: NavOptionsBuilder.() -> Unit
) : NavigationEvent()

object PopBackStack : NavigationEvent()