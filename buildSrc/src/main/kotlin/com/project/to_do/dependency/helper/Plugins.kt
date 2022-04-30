package com.project.to_do.dependency.helper

import org.gradle.api.Project

internal fun Project.addPlugins(plugin: Plugin) {
    plugin.plugins.forEach {
        plugins.apply(it)
    }
}