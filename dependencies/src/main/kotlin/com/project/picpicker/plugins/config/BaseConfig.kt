package com.project.picpicker.plugins.config

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.project.picpicker.LibsVersion
import com.project.picpicker.Modules.unsplashApi
import com.project.picpicker.extensions.addBuildFields
import com.project.picpicker.managers.DefaultPropertiesManager
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.baseConfig(isCompose: Boolean) {
    configure<BaseExtension> {
        if (isCompose) {
            composeOptions.useLiveLiterals = true
            composeOptions.kotlinCompilerExtensionVersion = LibsVersion.composeVersion
            buildFeatures.compose = true
        }

        defaultConfig {
            when (project.name) {
                unsplashApi -> {
                    val defaultPropertiesManager =
                        DefaultPropertiesManager(gradleLocalProperties(rootDir))
                    addBuildFields(defaultPropertiesManager)
                }
            }
        }
    }
}