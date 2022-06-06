package com.project.picpicker.plugins

import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.plugins.AppPlugin
import com.project.picpicker.Dependency.baseDependencyDeps
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.applyDependency
import com.project.picpicker.plugins.options.applicationOptions
import com.project.picpicker.plugins.options.libraryOptions
import com.project.picpicker.tasks.AutoInc
import org.gradle.api.Plugin
import org.gradle.api.Project
class AppModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            addCommonPlugins()
            plugins.all {
                when (this) {
                    is AppPlugin -> {
                        autoInc()
                        applicationOptions()
                        appApp()
                    }
                    is LibraryPlugin -> {
                        libraryOptions()
                    }
                }
            }
        }
    }
}


/** Default option for all project */
private fun Project.addCommonPlugins() {
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-kapt")

    applyDependency(
        deps(
            baseDependencyDeps
        )
    )
}

private fun Project.appApp() {
    plugins.apply("kotlin-kapt")
    plugins.apply("org.jetbrains.kotlin.android")
}

private fun Project.autoInc() {
    tasks.register("autoInc", AutoInc::class.java)
}