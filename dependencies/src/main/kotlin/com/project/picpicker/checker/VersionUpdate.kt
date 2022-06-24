package com.project.picpicker.checker

import com.github.benmanes.gradle.versions.updates.Coordinate
import com.github.benmanes.gradle.versions.updates.DependencyUpdates
import com.project.picpicker.checker.model.TomlLibVersion
import com.project.picpicker.checker.model.Version
import org.gradle.api.Project
import java.util.*

class VersionUpdate(
    private val project: Project
) {

    private val tomlHelper: TomlHelper = TomlHelper(project.parent ?: throw IllegalStateException())

    fun checkUpdates(): Boolean {
        val updatesChecker = DependencyUpdates(project)
        updatesChecker.revision = "release"
        updatesChecker.checkForGradleUpdate = false
        updatesChecker.checkConstraints = true
        val names = tomlHelper.listLibrary.map { it.module.name }
        updatesChecker.setResolutionStrategy {
            componentSelection.all {
                val lib = tomlHelper.listLibrary.find { names.contains(this.candidate.moduleIdentifier.name) }
                if (lib != null) {
                    println(" \"Library project\" -> $lib")
                    println(" \"Library new version\" -> ${candidate.displayName}")
                }
            }
        }
        val newVersion = updatesChecker.run().run {
            upgradeVersions
        }
        return findUpdateVersion(newVersion)
    }

    private fun findUpdateVersion(update: Map<Map<String, String>, Coordinate>): Boolean {
        var isUpdate = false
        update.forEach { newlib ->
            val findModule = newlib.value.toString().replace(":${newlib.value.version}", "")
            val tomlVersion = tomlHelper.currentTomlVersion(findModule)
            if (tomlVersion != null) {
                val lib = tomlHelper.findVersion(tomlVersion.tomlVersion)
                update(newlib.value, lib, tomlVersion)
                isUpdate = true
            } else {
                println("Not found version by $findModule")
            }
        }
        return isUpdate
    }

    private fun update(lib: Coordinate, oldVersion: String, tomlLibVersion: TomlLibVersion) {
        println("${lib.version} -> $oldVersion")
        if (
            lib.version.contains(oldVersion)
            || lib.version.toLowerCase(Locale.getDefault())
                .contains("m")
        ) return
        val version = Version(oldVersion, lib.version)
        tomlHelper.writeVersion(version, tomlLibVersion)
    }
}
