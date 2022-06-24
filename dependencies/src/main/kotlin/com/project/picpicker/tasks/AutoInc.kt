package com.project.picpicker.tasks

import com.project.picpicker.helper.Git
import com.project.picpicker.helper.VersionHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class AutoInc : DefaultTask() {

    private val versionHelper = VersionHelper(project.rootDir.path)

    @TaskAction
    fun run() {
        updateBrunchAndSetConfig()
        val result = Git.brunchName()
        val version = findVersion(result)
        println(version)
        if (result.isNotEmpty() && version.isNotEmpty()) {
            updateVersionByTag(version)
        } else {
            updateBuildVersion()
        }
        addAndPushChange(result)
    }

    private fun updateBrunchAndSetConfig() {
        Git.initConfig()
        Git.fetch()
    }

    private fun updateVersionByTag(version: String) {
        val versions = version.split(".").take(2).toMutableList()
        val majorNew = version[0].toInt()
        val minorNew = versions[1].toInt()
        val major = versionHelper.versionMajor()
        val minor = versionHelper.versionMinor()
        when {
            major > majorNew ->
                throw IllegalArgumentException(
                    "Mew major version is smaller than the current: current $major new $majorNew"
                )
            major == majorNew && minor > minorNew -> {
                throw IllegalArgumentException(
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
        val build = versionHelper.versionCode().toString()
        Git.runCommand(
            "git checkout -B ${Git.currentRootBranch(currentBrunch, major, minor)} " +
                    "origin/${Git.currentRootBranch(currentBrunch, major, minor)}"
        )
        Git.add(versionHelper.fileName())
        Git.commit(commitMessage(major, minor, build))
        Git.push()
    }

    private fun findVersion(tags: String): String = regex.find(tags)?.value ?: ""

    private fun commitMessage(major: String, minor: String, build: String): String =
        "[CI-skip] autoInc version code: $major.$minor ($build)"

    private companion object {
        val regex = "\\d+(\\.\\d+)?".toRegex()
    }
}