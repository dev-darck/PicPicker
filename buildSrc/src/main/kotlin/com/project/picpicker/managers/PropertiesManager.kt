package com.project.picpicker.managers

import java.util.*

abstract class PropertiesManager {
    abstract val properties: Properties

    fun getStringValue(name: String): String =
        properties.getProperty(name) ?: System.getenv(name).orEmpty()
}
