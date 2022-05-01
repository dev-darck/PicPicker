package com.project.picpicker.plugins.config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.project.picpicker.applicationPlugin
import com.project.picpicker.dependency.helper.*
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.application(
    appDependency: Dependency = EmptyDependency,
    plugins: Plugin = EmptyPlugins,
    baseAppModuleExtension: (BaseAppModuleExtension.() -> Unit) = {}
) {
    addPlugins(applicationPlugin + plugins)
    applyDependency(appDependency)
    configure<BaseAppModuleExtension> {
        baseAppModuleExtension()
    }
}