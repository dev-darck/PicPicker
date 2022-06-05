package com.project.picpicker.managers

import com.project.picpicker.managers.models.Property
import java.util.*

class SettingAppPropertiesManager(override val properties: Properties) : PropertiesManager()  {
    override val values: List<Property> = listOf(
        getStringProperty("ENABLED_LEAK_CANARY"),
    )

    private fun getStringProperty(key: String) = Property(
        type = Boolean::class.java.simpleName,
        name = key,
        value = getBooleanValue(key),
    )
}