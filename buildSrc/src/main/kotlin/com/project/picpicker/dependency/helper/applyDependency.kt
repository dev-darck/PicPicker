package com.project.picpicker.dependency.helper

import com.project.picpicker.dependency.NameSpec
import com.project.picpicker.dependency.ProjectSpec
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.applyDependency(
    appDependency: Dependency = EmptyDependency
) {
    dependencies {
        appDependency.dependency.forEach { dep ->
            when (dep) {
                is ProjectSpec -> {
                    addModuleTo(
                        dep.configurationName.name,
                        dep.target.project,
                    )
                }
                is NameSpec -> {
                    addDependencyTo(
                        dep.configurationName.name,
                        dep.name,
                    )
                }
            }
        }
    }
}