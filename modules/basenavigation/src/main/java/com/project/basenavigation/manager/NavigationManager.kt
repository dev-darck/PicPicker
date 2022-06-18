package com.project.basenavigation.manager

import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.ToolBarConfig

interface NavigationManager {
    fun listBottomScreen(): Sequence<BottomConfig>
    fun lisScreens(): Sequence<Config>
    fun startDestination(): String
    fun listToolbar(): Sequence<ToolBarConfig>
}