package com.project.picpicker.plugins.config

import com.android.build.gradle.LibraryExtension
import com.project.picpicker.dependency.helper.*
import com.project.picpicker.libraryPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.module(
    appDependency: Dependency = Dependency.EMPTY,
    plugins: Plugin = EmptyPlugins,
    enabledCompose: Boolean = true,
    androidLibraryConfiguration: (LibraryExtension.() -> Unit) = {}
) {
    addPlugins(libraryPlugin + plugins)
    applyDependency(appDependency)
    baseConfig(enabledCompose)

    configure<LibraryExtension> {
        androidLibraryConfiguration()
    }
}