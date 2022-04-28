package com.project.to_do.tasks

import com.project.to_do.helper.Git
import com.project.to_do.helper.VersionHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class AutoInc : DefaultTask() {

    private val versionHelper = VersionHelper(project.rootDir.path)

    @TaskAction
    fun run() {
        updateBrunch()
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

    private fun updateBrunch() {
        println("add user.email ${Git.runCommand("git config --local user.email github-ci@aaa.ru")}")
        println("add user.name ${Git.runCommand("git config --local user.name Auto-inc")}")
        println("show list config ${Git.runCommand("git config --list")}")
        println("fetch -> ${Git.runCommand("git fetch origin")}")
        println("pull -> ${Git.runCommand("git pull")}")
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
        val build = versionHelper.versionCode().toString()
        println("git log -> ${Git.runCommand("git log --oneline --graph")}")
        println(
            "update and checkout " + Git.runCommand(
                "\"git checkout -B ${
                    currentRootBranch(
                        currentBrunch,
                        major,
                        minor
                    )
                } origin/${currentRootBranch(currentBrunch, major, minor)}\""
            )
        )
        println("brunch -> " + Git.runCommand("git branch --show-current"))
        println("add -> " + Git.runCommand("git add ${versionHelper.fileName()}"))
        println("commit -> " + Git.commit("\"[CI-skip] autoInc version code: $major.$minor ($build)\""))
        println("status -> " + Git.runCommand("git status"))
        println("push -> " + Git.runCommand("git push"))
        println("status -> " + Git.runCommand("git status"))
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