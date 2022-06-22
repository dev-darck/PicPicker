package com.project.picpicker.dependency.helper

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.applyDependency(
    appDependency: Dependency = Dependency()
) {
    dependencies {
        appDependency.forEach(
            { nameSpec ->
                if (nameSpec.dependencyNotation != null) {
                    addDependencyByConfigTo(
                        nameSpec.configurationName.name,
                        nameSpec.dependencyNotation,
                        nameSpec.externalModuleAction
                    )
                } else {
                    addDependencyTo(
                        nameSpec.configurationName.name,
                        nameSpec.name,
                        nameSpec.externalModuleAction
                    )
                }
            },
            { projectSpec ->
                addModuleTo(
                    projectSpec.configurationName.name,
                    projectSpec.target.project,
                )
            })
    }
}