package com.project.picpicker.tasks

import com.project.picpicker.checker.VersionUpdate
import com.project.picpicker.helper.Git
import com.project.picpicker.helper.TomlHelper
import com.project.picpicker.helper.VersionHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class UpdateDependenciesVersions : DefaultTask() {

    private val versionHelper = VersionHelper(project.rootDir.path)

    @TaskAction
    fun run() {
        val update = VersionUpdate(project)
        val isDepUpdate = update.checkUpdates()
        if (isDepUpdate) {
            updateBrunchAndSetConfig()
            val result = Git.brunchName()
            addAndPushChange(result)
        } else {
            println("These dependency versions are up to date or an update error has occurred")
        }
    }

    private fun updateBrunchAndSetConfig() {
        Git.initConfig()
        Git.fetch()
    }

    private fun addAndPushChange(currentBrunch: String) {
        val major = versionHelper.versionMajor().toString()
        val minor = versionHelper.versionMinor().toString()
        Git.runCommand(
            "git checkout -B ${Git.currentRootBranch(currentBrunch, major, minor)} " +
                    "origin/${Git.currentRootBranch(currentBrunch, major, minor)}"
        )
        Git.add(TomlHelper.TOML_FILE)
        Git.commit(commitMessage())
        Git.push()
    }

    private fun commitMessage(): String =
        "[CI-skip] auto update dependency versions"
}