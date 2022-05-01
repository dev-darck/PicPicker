package com.project.picpicker.dependency

import org.gradle.api.Project

sealed class ConfigurationName(val name: String)
object Impl : ConfigurationName("implementation")
object TestImpl : ConfigurationName("testImplementation")
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
) : AppDependency(name, config)

class Target(val project: Project) {
    val name: String = project.name
}