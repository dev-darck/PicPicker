package com.project.picpicker.checker

import com.github.benmanes.gradle.versions.updates.Coordinate
import com.github.benmanes.gradle.versions.updates.DependencyUpdates
import com.project.picpicker.checker.model.TomlLibVersion
import com.project.picpicker.checker.model.Version
import com.project.picpicker.helper.TomlHelper
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
        updatesChecker.setResolutionStrategy {
            componentSelection.all {
                val findModule = candidate.displayName.replace(":${candidate.version}", "")
                val libName = tomlHelper.currentTomlLib(findModule)
                if (libName != null) {
                    val lib = tomlHelper.findLibrary(libName)
                    println(" \"Library project\" -> $lib")
                    println(" \"Library new version\" -> ${candidate.displayName}")
                }
            }
        }
        val newVersion = updatesChecker.run().run {
            latestVersions
        }
        return findUpdateVersion(newVersion)
    }

    private fun findUpdateVersion(update: Map<Map<String, String>, Coordinate>): Boolean {
        var isUpdate = false
        val currentLibraries = tomlHelper.listLibrary
        val updateVersion = mutableListOf<Triple<Coordinate, String, TomlLibVersion>>()
        currentLibraries.forEach { currentLib ->
            val dep = currentLib.toString().dropLastDelimiter()
            val newDep = update.values.find { it.toString().dropLastDelimiter().contains(dep) }
            val tomlVersion = tomlHelper.currentTomlVersion(dep)
            if (newDep != null && tomlVersion != null) {
                println("Update version newLib -> $newDep currentLib -> $currentLib")
                val lib = tomlHelper.findVersion(tomlVersion.tomlVersion)
                updateVersion.add(Triple(newDep, lib, tomlVersion))
                isUpdate = true
            } else {
                println("Not found version by newLib $newDep currentLib $currentLib")
            }
        }
        if (isUpdate || updateVersion.isNotEmpty()) {
            update(updateVersion)
        }
        return isUpdate
    }

    private fun String.dropLastDelimiter(): String {
        val findLastIndex = this.indexOfLast { DELIMITER.contains(it) }
        return this.removeRange(findLastIndex, this.length)
    }

    private fun update(list: List<Triple<Coordinate, String, TomlLibVersion>>) {
        val libs = list.map { (lib, oldVersion, tomlLibVersion) ->
            println("${lib.version} -> $oldVersion")
            if (
                lib.version.contains(oldVersion)
                || lib.version.toLowerCase(Locale.getDefault())
                    .contains("m")
            ) {
                Version(oldVersion, oldVersion, tomlLibVersion)
            } else {
                Version(oldVersion, lib.version, tomlLibVersion)
            }
        }
        tomlHelper.writeVersion(libs)
    }

    private companion object {
        const val DELIMITER = ":"
    }
}
