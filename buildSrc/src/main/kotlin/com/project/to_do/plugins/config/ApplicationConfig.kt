package com.project.to_do.plugins.config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.project.to_do.applicationPlugin
import com.project.to_do.dependency.helper.*
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