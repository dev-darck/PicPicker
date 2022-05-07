package com.project.navigationapi.config

interface BottomConfig: Config {
    val icon: Int
    val isRoot: Boolean get() = false
    val order: Int
}