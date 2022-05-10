package com.project.picpicker.managers

import com.project.picpicker.managers.models.Property
import java.util.*

class DefaultPropertiesManager(override val properties: Properties) : PropertiesManager() {
    private val unsplash =
        Property(String::class.java.simpleName, "BASE_URL", "https://api.unsplash.com/")

    val values: List<Property> = listOf(
        unsplash,
        getStringProperty("UNSPLASH_ACCESS_KEY"),
        getStringProperty("UNSPLASH_SECRET_KEY"),
    )

    private fun getStringProperty(key: String) = Property(
        type = String::class.java.simpleName,
        name = key,
        value = getStringValue(key),
    )
}