package com.project.picpicker.checker.model

data class Version(
    val oldVersion: String = "",
    var newVersion: String = "",
    val tomlVersion: TomlLibVersion = TomlLibVersion()
)
