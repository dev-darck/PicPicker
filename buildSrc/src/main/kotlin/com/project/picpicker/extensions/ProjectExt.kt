package com.project.picpicker.extensions

import com.project.picpicker.debug
import com.project.picpicker.release
import com.project.picpicker.managers.DebugSigningPropertiesManager
import com.project.picpicker.managers.ReleaseSigningPropertiesManager
import org.gradle.api.Project

fun Project.getSigningProperties(signingName: String) = when (signingName) {
    debug -> DebugSigningPropertiesManager(projectDir.path)
    release -> ReleaseSigningPropertiesManager(projectDir.path)
    else -> throw IllegalArgumentException("Unknown signingName = $signingName")
}