package com.project.navigationapi.config


interface ToolBarConfig {
    val route: Route
    val lable: Int
    val leftBottom: BottomIcon?
        get() = null
    val rightBottom: BottomIcon?
        get() = null
    val toolbarArgument: List<String>
        get() = emptyList()
}

val <T: ToolBarConfig> T.root get() = this.route.routeScheme