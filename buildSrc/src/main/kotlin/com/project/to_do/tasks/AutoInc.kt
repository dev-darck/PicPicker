package com.project.to_do.tasks

import com.project.to_do.helper.Git
import com.project.to_do.helper.VersionHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class AutoInc : DefaultTask() {

    private val versionHelper = VersionHelper(project.rootDir.path)

    @TaskAction
    fun run() {
        println("restore " + Git.runCommand("git restore version/version.properties"))
        println("status " + Git.runCommand("git status"))
        val result = Git.runCommand("git rev-parse --abbrev-ref HEAD")
        println(result)
        val version = findVersion(result)
        if (result.isNotEmpty() && version.isNotEmpty()) {
            updateVersionByTag(version)
        } else {
            println("update build version")
            updateBuildVersion()
        }
        addAndPushChange(result)
    }

    private fun updateVersionByTag(version: String) {
        val versions = version.split(".").take(2).toMutableList()
        val majorNew = version[0].toInt()
        val minorNew = versions[1].toInt()
        val major = versionHelper.versionMajor()
        val minor = versionHelper.versionMinor()
        when {
            major > majorNew ->
                throw IllegalStateException(
                    "Mew major version is smaller than the current: current $major new $majorNew"
                )
            major == majorNew && minor > minorNew -> {
                throw IllegalStateException(
                    "New minor version is smaller than the current: current $minor new $minorNew"
                )
            }
            major == majorNew && minor == minorNew -> {
                updateBuildVersion()
            }
            else -> {
                versions.add("0")
                versionHelper.setNewVersion(versions)
            }
        }
    }

    private fun updateBuildVersion() {
        val major = versionHelper.versionMajor().toString()
        val minor = versionHelper.versionMinor().toString()
        val build = (versionHelper.versionCode() + 1).toString()
        versionHelper.setNewVersion(listOf(major, minor, build))
    }

    private fun addAndPushChange(currentBrunch: String) {
        val major = versionHelper.versionMajor().toString()
        val minor = versionHelper.versionMinor().toString()
        val build = versionHelper.versionCode()
        Git.runCommand("git checkout ${currentRootBranch(currentBrunch, major, minor)}")
        println("status " + Git.runCommand("git status"))
        var result = ""
        result = "add \n " + Git.runCommand("git add version/version.properties")
        println(result)
        println("status " + Git.runCommand("git status"))
        result =  "commit \n " + Git.commit("version/version.properties","\'autoInc version code: $major.$minor - $build\'")
        println(result)
        println("status " + Git.runCommand("git status"))
        result = "push \n " + Git.runCommand("git push HEAD:${currentRootBranch(currentBrunch, major, minor)}")
        println(result)
        println("status " + Git.runCommand("git status"))
    }

    private fun currentRootBranch(currentBrunch: String, major: String, minor: String) =
        if (currentBrunch.contains(release)) {
            release + major + minor
        } else {
            dev
        }

    private fun findVersion(tags: String): String {
        return regex.find(tags)?.value ?: ""
    }

    private companion object {
        val regex = "\\d+(\\.\\d+)?".toRegex()
        const val release = "release/"
        const val dev = "dev"
    }
}