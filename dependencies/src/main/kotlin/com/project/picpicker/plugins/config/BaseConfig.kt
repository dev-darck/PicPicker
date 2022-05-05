package com.project.picpicker.plugins.config

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.baseConfig(isCompose: Boolean) {
    if (isCompose) {
        configure<BaseExtension> {
            composeOptions.useLiveLiterals = true
            composeOptions.kotlinCompilerExtensionVersion = "1.2.0-alpha08"
            buildFeatures.compose = true
        }
    }
}