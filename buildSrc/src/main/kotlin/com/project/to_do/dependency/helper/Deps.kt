package com.project.to_do.dependency.helper

import com.project.to_do.dependency.*
import com.project.to_do.dependency.Target
import org.gradle.api.Project


sealed class Dependency(
    val dependency: Deps = emptySequence()
)

object EmptyDependency : Dependency()

data class ProjectDependency(
    val targets: Sequence<ProjectSpec> = emptySequence()
) : Dependency(targets)

class NamedDependency(
    private val names: Sequence<NameSpec> = emptySequence()
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

val String.impl: NameSpec
    get() = NameSpec(this, Impl)
val String.test: NameSpec
    get() = NameSpec(this, TestImpl)
val String.androidTest: NameSpec
    get() = NameSpec(this, AndroidTestImpl)
val String.kapt: NameSpec
    get() = NameSpec(this, Kapt)
val String.compile: NameSpec
    get() = NameSpec(this, CompileOnly)
