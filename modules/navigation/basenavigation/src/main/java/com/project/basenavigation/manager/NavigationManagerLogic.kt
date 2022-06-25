package com.project.basenavigation.manager

import com.project.navigationapi.config.BottomConfig
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.ToolBarConfig
import com.project.util.extensions.convertTo

class NavigationManagerLogic(
    var screens: Sequence<Config>
) : NavigationManager {

    var startDestination: String = ""
    var bottomConfig: Sequence<BottomConfig> = emptySequence()
    var toolBarConfig: Sequence<ToolBarConfig> = emptySequence()

    init {
        toolBarConfig = screens.convertTo()
        bottomConfig = screens.convertTo<BottomConfig>().sortedBy(BottomConfig::order)
        startDestination = bottomConfig.find(BottomConfig::isRoot)?.route?.routeScheme.orEmpty()
    }

    override fun listBottomScreen(): Sequence<BottomConfig> = bottomConfig

    override fun lisScreens(): Sequence<Config> = screens

    override fun startDestination(): String = startDestination

    override fun listToolbar() = toolBarConfig
}