package com.project.to_do.plugins

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.plugins.AppPlugin
import com.project.to_do.dependency.helper.addDep
import com.project.to_do.dependency.helper.applyDependency
import com.project.to_do.dependency.helper.impl
import com.project.to_do.plugins.options.applicationOptions
import com.project.to_do.plugins.options.libraryOptions
import com.project.to_do.tasks.AutoInc
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AppModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.all {
                addCommonPlugins()
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

private fun Project.addCommonPlugins() {
    plugins.apply("kotlin-android")
    configure<BaseExtension> {
        composeOptions.useLiveLiterals = true
        composeOptions.kotlinCompilerExtensionVersion = "1.0.5"
    }
    applyDependency(
        addDep(
            "androidx.core:core-ktx:1.7.0".impl,
            "androidx.appcompat:appcompat:1.4.1".impl,
            "com.google.android.material:material:1.5.0".impl,
            "junit:junit:4.13.2".impl,
            "androidx.test.ext:junit:1.1.3".impl,
            "androidx.test.espresso:espresso-core:3.4.0".impl,
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