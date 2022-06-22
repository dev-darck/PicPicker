package com.project.picpicker.dependency.helper

import com.project.picpicker.dependency.*
import com.project.picpicker.dependency.Annotation
import com.project.picpicker.dependency.Target
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.provider.Provider

fun deps(vararg project: ProjectSpec): Dependency =
    Dependency(project.asSequence())

fun deps(vararg dependencies: NameSpec): Dependency =
    dependencies.asSequence().let(::Dependency)

fun deps(vararg dependencies: Dependency): Dependency = dependencies.reduce(Dependency::plus)

fun deps(vararg dependencies: AppDependency): Dependency = Dependency(dependencies.asSequence())

fun Project.module(projName: String): ProjectSpec = ProjectSpec(Target(project(":$projName")), Impl)

fun Project.apiModule(projName: String): ProjectSpec = ProjectSpec(Target(project(":$projName")), Api)

infix operator fun Dependency.plus(dep: Dependency): Dependency = Dependency(
    dependency + dep.dependency
)

val String.impl: NameSpec
    get() = NameSpec(this, Impl)
val String.api: NameSpec
    get() = NameSpec(this, Api)

fun String.impl(externalModuleAction: (ExternalModuleDependency.() -> Unit)): NameSpec =
    NameSpec(this, Impl, externalModuleAction = externalModuleAction)

fun Provider<*>.impl(externalModuleAction: (ExternalModuleDependency.() -> Unit)): NameSpec
     = NameSpec(dependencyNotation = this, config = Impl, name = "", externalModuleAction = externalModuleAction)

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

val Provider<*>.impl: NameSpec
    get() = NameSpec(dependencyNotation = this, config = Impl, name = "")
val Provider<*>.kapt: NameSpec
    get() = NameSpec(dependencyNotation = this, config = Kapt, name = "")

val Provider<*>.androidTest: NameSpec
    get() = NameSpec(dependencyNotation = this, config = AndroidTestImpl, name = "")

val Provider<*>.debugImpl: NameSpec
    get() = NameSpec(dependencyNotation = this, config = DebugImpl, name = "")
val Provider<*>.test: NameSpec
    get() = NameSpec(dependencyNotation = this, config = TestImpl, name = "")
