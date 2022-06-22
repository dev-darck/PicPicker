package com.project.picpicker.dependency

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency

sealed class ConfigurationName(val name: String)
object Impl : ConfigurationName("implementation")
object Api : ConfigurationName("api")
object TestImpl : ConfigurationName("testImplementation")
object Annotation : ConfigurationName("annotationProcessor")
object Kapt : ConfigurationName("kapt")
object AndroidTestImpl : ConfigurationName("androidTestImplementation")
object CompileOnly : ConfigurationName("compileOnly")
object RuntimeOnly : ConfigurationName("runtimeOnly")
object DebugImpl : ConfigurationName("debugImplementation")

sealed class AppDependency(
    open val name: String,
    val configurationName: ConfigurationName,
)

data class ProjectSpec(
    val target: Target,
    val config: ConfigurationName,
) : AppDependency(target.name, config) {
    override fun toString(): String {
        return "Target = ${target.name} ConfigurationName = ${config.name}"
    }
}

data class NameSpec(
    override val name: String,
    val config: ConfigurationName,
    val dependencyNotation: org.gradle.api.provider.Provider<*>? = null,
    val externalModuleAction: ExternalModuleDependency.() -> Unit = {}
) : AppDependency(name, config)

class Target(val project: Project) {
    val name: String = project.name
}