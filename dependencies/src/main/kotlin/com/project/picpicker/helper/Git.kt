package com.project.picpicker.helper

private const val commit = "commit"
private const val push = "push"
private const val git = "git"
private const val release = "release/"
private const val dev = "dev"

object Git {

    fun initConfig() {
        runCommand("git config --local user.email bot@bot.com", "config --local")
        runCommand("git config --local user.name Bot", "config --local")
    }

    fun fetch() {
        runCommand("git fetch origin", "fetch")
    }

    fun brunchName(): String {
        return runCommand("git branch --show-current", "branch")
    }

    fun push(branchName: String = "") {
        if (branchName.isNotEmpty()) {
            runCommand("$git $push $branchName", push)
        } else {
            runCommand("$git $push", push)
        }
    }

    fun add(fileName: String) {
        runCommand("git add $fileName", "add")
    }

    fun commit(message: String) {
        process(listOf(git, commit, "-m", message))
            ?.printResult(commit)
    }

    fun runCommand(command: String, gitCommand: String = ""): String {
        val commands = command.split("\\s".toRegex())
        return process(commands)?.printResult(gitCommand) ?: ""
    }

    fun currentRootBranch(currentBrunch: String, major: String, minor: String) =
        if (currentBrunch.contains(release)) {
            release + major + minor
        } else {
            dev
        }

    private fun process(command: List<String>): Process? {
        return try {
            ProcessBuilder(command)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .start()
        } catch (e: Exception) {
            println(e)
            null
        }
    }

    private fun Process.printResult(command: String): String {
        val result = inputStream.bufferedReader().use { it.readText() }
        println("$command -> $result")
        return result
    }
}