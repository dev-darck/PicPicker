package com.project.navigationapi.config


interface ToolBarConfig {
    val route: Route
    val lable: Int
    val arguments: List<String>
        get() = emptyList()
}