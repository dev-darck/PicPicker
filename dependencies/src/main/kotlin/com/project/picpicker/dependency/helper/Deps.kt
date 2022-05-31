package com.project.picpicker.dependency.helper

import com.project.picpicker.dependency.*
import com.project.picpicker.dependency.Annotation
import com.project.picpicker.dependency.Target
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency


sealed class Dependency(
    val dependency: Deps = emptySequence(),
)

object EmptyDependency : Dependency()

data class ProjectDependency(
    val targets: Sequence<ProjectSpec> = emptySequence(),
) : Dependency(targets)

class NamedDependency(
    private val names: Sequence<NameSpec> = emptySequence(),
) : Dependency(names)

data class MixedDependency(
    val names: Sequence<NameSpec> = emptySequence(),
    val targets: Sequence<ProjectSpec> = emptySequence(),
) : Dependency(targets + names)


typealias Deps = Sequence<AppDependency>

fun addDep(vararg project: ProjectSpec): ProjectDependency =
    ProjectDependency(project.asSequence())

fun addDep(vararg dependencies: NameSpec): NamedDependency =
    dependencies.asSequence().let(::NamedDependency)

val Deps.names: Sequence<NameSpec>
    get(): Sequence<NameSpec> = filterIsInstance(NameSpec::class.java)

val Deps.targets: Sequence<ProjectSpec>
    get(): Sequence<ProjectSpec> = filterIsInstance(ProjectSpec::class.java)

fun Project.module(name: String): ProjectSpec = ProjectSpec(Target(project(":$name")), Impl)

infix operator fun Dependency.plus(dep: Dependency): MixedDependency = MixedDependency(
    dependency.names + dep.dependency.names,
    dependency.targets + dep.dependency.targets,
)

infix operator fun Dependency.plus(dep: NamedDependency): NamedDependency = NamedDependency(
    dependency.names + dep.dependency.names
)

val String.impl: NameSpec
    get() = NameSpec(this, Impl)

fun String.impl(externalModuleAction: (ExternalModuleDependency.() -> Unit)): NameSpec =
    NameSpec(this, Impl, externalModuleAction)

val String.test: NameSpec
    get() = NameSpec(this, TestImpl)
val String.annotation: NameSpec
    get() = NameSpec(this, Annotation)
val String.androidTest: NameSpec
    get() = NameSpec(this, AndroidTestImpl)
val String.kapt: NameSpec
    get() = NameSpec(this, Kapt)
val String.compile: NameSpec
    get() = NameSpec(this, CompileOnly)
val String.runtime: NameSpec
    get() = NameSpec(this, RuntimeOnly)
val String.debugImpl: NameSpec
    get() = NameSpec(this, DebugImpl)
