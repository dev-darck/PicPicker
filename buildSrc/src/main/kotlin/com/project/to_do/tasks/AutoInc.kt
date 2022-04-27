package com.project.to_do.tasks

import com.project.to_do.helper.Git
import com.project.to_do.helper.VersionHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class AutoInc : DefaultTask() {

    private val versionHelper = VersionHelper(project.rootDir.path)

    @TaskAction
    fun run() {
        println(Git.runCommand("git remote -v"))
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
        println("new version build")
    }

    private fun addAndPushChange(currentBrunch: String) {
        val major = versionHelper.versionMajor().toString()
        val minor = versionHelper.versionMinor().toString()
        val build = versionHelper.versionCode()
        println("start commit")
        var result = ""
        result = "DIF \n " + Git.runCommand("git diff")
        println(result)
        result = "add \n " + Git.runCommand("git add .")
        result = Git.runCommand("git add */version/version.properties")
        println(result)
        result =  "commit \n " + Git.runCommand("git commit -m autoInc version code: $major.$minor - $build")
        println(result)
        result = "push \n " + Git.runCommand(
            "git push HEAD:${
                if (currentBrunch.contains(release)) {
                    release + major + minor
                } else {
                    dev
                }
            }"
        )
        println(result)
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