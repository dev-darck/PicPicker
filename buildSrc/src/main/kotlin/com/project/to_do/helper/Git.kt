package com.project.to_do.helper

import org.apache.tools.ant.taskdefs.Execute.runCommand




object Git {

    fun runCommand(command: String): String {
        val commands = command.split("\\s".toRegex())
        try {
            return senCommand(commands).inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            print(e)
        }
        return ""
    }

    fun runCommand(command: List<String>): String {
        try {
            return senCommand(command).inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            print(e)
        }
        return ""
    }

    fun gitCommit(file: String, message: String): String {
        try {
            return  senCommand(listOf("git", "commit", "$file ", "-m", message)).inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            print(e)
        }
        return ""

    }

    private fun senCommand(command: List<String>): Process {
        return ProcessBuilder(command)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .start()
    }
}