package com.project.picpicker.dependency.helper

import org.gradle.api.Project

internal fun Project.addPlugins(plugin: Plugin) {
    plugin.plugins.forEach {
        plugins.apply(it)
    }
}