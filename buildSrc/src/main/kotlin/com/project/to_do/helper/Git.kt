package com.project.to_do.helper

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

    private fun senCommand(command: List<String>): Process {
        return ProcessBuilder(command).start()
    }
}