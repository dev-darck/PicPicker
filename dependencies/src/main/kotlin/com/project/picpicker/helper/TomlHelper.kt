package com.project.picpicker.helper

import com.project.picpicker.checker.model.TomlLibVersion
import com.project.picpicker.checker.model.Version
import com.project.picpicker.depToml
import com.project.picpicker.safeFindLibrary
import com.project.picpicker.safeFindVersion
import org.gradle.api.Project

class TomlHelper(val project: Project) {

    private val libToml = project.rootDir.walk().maxDepth(2).find { it.name.contains(TOML_VERSION) }
    private val contentToml = lazy { libToml?.inputStream()?.bufferedReader()?.use { it.readText() } }
    val listLibrary = project.depToml.libraryAliases.map {
        project.depToml.safeFindLibrary(it).get()
    }

    fun findVersion(name: String): String = project.depToml.safeFindVersion(name).displayName

    fun findLibrary(name: String) = project.depToml.findLibrary(name).orElseGet {
        null
    }?.get()

    fun currentTomlLib(module: String): String? {
        val result = contentToml.value ?: return null
        val startSearch = result.indexOf(LIBRARIES)
        val textForSearch = result.subSequence(startSearch + LIBRARIES.length, result.length)
        return textForSearch.lines().find { it.contains(module) }?.split(" ")?.first()
    }

    fun currentTomlVersion(module: String): TomlLibVersion? {
        val result = contentToml.value ?: return null
        val startSearch = result.indexOf(LIBRARIES)
        val textForSearch = result.subSequence(startSearch + LIBRARIES.length, result.length)
        val findModule = "module = \"$module\""
        val match = textForSearch.lines().find { it.contains(findModule) }
        return if (match != null) {
            val position = match.indexOf(VERSION) + VERSION.length
            val versionName = match.subSequence(position, match.lastIndex).replace("\"".toRegex(), "").trim()
            TomlLibVersion(
                module,
                versionName,
            )
        } else {
            null
        }
    }

    fun writeVersion(libs: List<Version>) {
        if (libToml == null) {
            println("This file is not found \"$TOML_VERSION\"")
            return
        }
        var result = contentToml.value ?: return
        libs.forEach { version ->
            println("Update $version")
            if (version.newVersion.isNotEmpty() && version.oldVersion.isNotEmpty()) {
                result = result.replace(
                    "${version.tomlVersion.tomlVersion} = \"${version.oldVersion}\"",
                    "${version.tomlVersion.tomlVersion} = \"${version.newVersion}\""
                )
            } else {
                println("New version or old version is empty")
            }
        }
        libToml.bufferedWriter().use { it.write(result) }
    }

    companion object {
        const val TOML_FILE = "gradle/libs.versions.toml"
        private const val TOML_VERSION = "libs.versions.toml"
        private const val VERSION = "version.ref = "
        private const val LIBRARIES = "[libraries]"

    }
}
