package com.project.picpicker.managers

import com.project.picpicker.managers.models.Property
import java.util.*

class DefaultPropertiesManager(override val properties: Properties) : PropertiesManager() {

    val values: List<Property> = listOf(
        getStringProperty("UNSPLASH_ACCESS_KEY"),
        getStringProperty("UNSPLASH_SECRET_KEY"),
    )

    private fun getStringProperty(key: String) = Property(
        type = String::class.java.simpleName,
        name = key,
        value = getStringValue(key),
    )
}