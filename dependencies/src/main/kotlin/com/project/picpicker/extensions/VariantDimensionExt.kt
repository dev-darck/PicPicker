package com.project.picpicker.extensions

import com.android.build.api.dsl.VariantDimension
import com.project.picpicker.managers.PropertiesManager
import com.project.picpicker.managers.models.Property

fun VariantDimension.addBuildFields(defaultPropertiesManager: PropertiesManager) {
    defaultPropertiesManager.values.forEach(::addBuildField)
}

private fun VariantDimension.addBuildField(property: Property) {
    val value = if (property.type == "String") "\"${property.value}\"" else property.value
    buildConfigField(property.type, property.name, value)
}