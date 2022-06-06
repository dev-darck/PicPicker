package com.project.picpicker.dependency.helper

import com.project.picpicker.dependency.AppDependency
import com.project.picpicker.dependency.NameSpec
import com.project.picpicker.dependency.ProjectSpec

data class Dependency(
    val dependency: Deps = emptySequence(),
) {
    companion object {
        val EMPTY = Dependency()
    }
}

fun Dependency.forEach(
    nameAction: (NameSpec) -> Unit = {},
    projectAction: (ProjectSpec) -> Unit = {},
) {
    dependency.forEach loop@{ dep ->
        return@loop when (dep) {
            is NameSpec -> nameAction(dep)
            is ProjectSpec -> projectAction(dep)
        }
    }
}


typealias Deps = Sequence<AppDependency>
