package com.project.picpicker.dependency.helper

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.applyDependency(
    appDependency: Dependency = Dependency()
) {
    dependencies {
        appDependency.forEach(
            { nameSpec ->
                addDependencyTo(
                    nameSpec.configurationName.name,
                    nameSpec.name,
                    nameSpec.externalModuleAction
                )
            },
            { projectSpec ->
                addModuleTo(
                    projectSpec.configurationName.name,
                    projectSpec.target.project,
                )
            })
    }
}