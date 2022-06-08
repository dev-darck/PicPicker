package com.project.navigationapi.config


interface ToolBarConfig {
    val route: Route
    val label: Int?
        get() = null
    val leftBottom: BottomIcon?
        get() = null
    val rightBottom: BottomIcon?
        get() = null
    val isTransparentBackground: Boolean
        get() = false
    val toolbarArgument: List<String>
        get() = emptyList()
}

val <T: ToolBarConfig> T.root get() = this.route.routeScheme