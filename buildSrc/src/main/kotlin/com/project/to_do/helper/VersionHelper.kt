package com.project.to_do.helper

import java.io.File
import java.io.FileInputStream
import java.lang.NullPointerException
import java.util.*

class VersionHelper(private val rootDir: String) {

    private val versionProps: Properties
        get() {
            val versionProperties = File("${rootDir}$path")
            val properties = Properties()
            if (versionProperties.exists()) {
                properties.load(FileInputStream(versionProperties))
            } else {
                throw NullPointerException("File by directory ${rootDir}$path not exist")
            }
            return properties
        }

    fun versionName(): String {
        val major = versionProps[VERSION_MAJOR].toString().toInt()
        val minor = versionProps[VERSION_MINOR].toString().toInt()
        val build = versionProps[VERSION_BUILD].toString().toInt()
        return "$major.$minor.$build"
    }

    fun versionMajor(): Int = versionProps[VERSION_MAJOR].toString().toInt()

    fun versionMinor(): Int = versionProps[VERSION_MINOR].toString().toInt()

    fun versionCode(): Int = versionProps[VERSION_BUILD].toString().toInt()

    fun setNewVersion(versions: List<String>) {
        if (versions.size < 3) throw ArrayIndexOutOfBoundsException("Versions have not be < 3 elements $versions")
        val propertiesFile = File(rootDir + path)
        val newVersion = "$VERSION_MAJOR = ${versions[0]}\n" +
                "$VERSION_MINOR = ${versions[1]}\n" +
                "$VERSION_BUILD = ${versions[2]}"
        propertiesFile.bufferedWriter().use { it.write(newVersion) }
    }

    private companion object {
        const val path = "/version/version.properties"
        const val VERSION_MAJOR = "VERSION_MAJOR"
        const val VERSION_MINOR = "VERSION_MINOR"
        const val VERSION_BUILD = "VERSION_BUILD"
    }
}