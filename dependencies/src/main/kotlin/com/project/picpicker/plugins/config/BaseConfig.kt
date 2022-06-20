package com.project.picpicker.plugins.config

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.project.picpicker.LibsVersion
import com.project.picpicker.Modules.app
import com.project.picpicker.Modules.settings
import com.project.picpicker.Modules.unsplashApi
import com.project.picpicker.extensions.addBuildFields
import com.project.picpicker.helper.VersionHelper
import com.project.picpicker.managers.DefaultPropertiesManager
import com.project.picpicker.managers.SettingAppPropertiesManager
import com.project.picpicker.managers.models.SettingsConfig
import com.project.picpicker.release
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.*

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

                app -> {
                    val isRelease =
                        gradle.startParameter.taskNames.toString().toLowerCase(Locale.getDefault()).contains(release)
                    val appName = rootProject.name
                    val settingsConfig = SettingsConfig(VersionHelper(rootDir.path).versionConfig(isRelease), appName)
                    val settingAppPropertiesManager =
                        SettingAppPropertiesManager(gradleLocalProperties(rootDir), settingsConfig)
                    addBuildFields(settingAppPropertiesManager)
                }

                settings -> {
                    val isRelease =
                        gradle.startParameter.taskNames.toString().toLowerCase(Locale.getDefault()).contains(release)
                    val appName = rootProject.name
                    val settingsConfig = SettingsConfig(VersionHelper(rootDir.path).versionConfig(isRelease), appName)
                    val settingAppPropertiesManager =
                        SettingAppPropertiesManager(gradleLocalProperties(rootDir), settingsConfig)
                    addBuildFields(settingAppPropertiesManager)
                }
            }
        }
    }
}