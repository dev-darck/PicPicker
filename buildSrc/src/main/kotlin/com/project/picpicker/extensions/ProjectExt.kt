package com.project.picpicker.extensions

import com.project.picpicker.debug
import com.project.picpicker.release
import com.project.picpicker.storage.DebugSigningProperties
import com.project.picpicker.storage.ReleaseSigningProperties
import org.gradle.api.Project

fun Project.getSigningProperties(signingName: String) = when (signingName) {
    debug -> DebugSigningProperties(projectDir.path)
    release -> ReleaseSigningProperties(projectDir.path)
    else -> throw IllegalArgumentException("Unknown signingName = $signingName")
}