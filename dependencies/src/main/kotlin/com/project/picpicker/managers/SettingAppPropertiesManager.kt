package com.project.picpicker.managers

import com.project.picpicker.managers.models.Property
import com.project.picpicker.managers.models.SettingsConfig
import java.util.*

class SettingAppPropertiesManager(
    override val properties: Properties,
    val settings: SettingsConfig = SettingsConfig()
) : PropertiesManager() {

    private val versionApp =
        Property(String::class.java.simpleName, "VERSION_APP", settings.version)

    private val appName =
        Property(String::class.java.simpleName, "APP_NAME", settings.appName)

    override val values: List<Property>
        get() {
            val result = mutableListOf(
                versionApp,
                appName,
                getStringProperty("ENABLED_LEAK_CANARY"),
            )
            when {
                settings.appName.isNotEmpty() -> result.add(appName)
                settings.version.isNotEmpty() -> result.add(versionApp)
            }
            return result
        }


    private fun getStringProperty(key: String) = Property(
        type = Boolean::class.java.simpleName,
        name = key,
        value = getBooleanValue(key),
    )
}