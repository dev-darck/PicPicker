package com.project.picpicker.managers

import com.project.picpicker.managers.models.Property
import java.util.*

abstract class PropertiesManager {
    abstract val properties: Properties
    open val values: List<Property> = emptyList()

    fun getStringValue(name: String): String =
        properties.getProperty(name) ?: System.getenv(name).orEmpty()

    fun getBooleanValue(name: String): String =
        properties.getProperty(name) ?: "false"
}
