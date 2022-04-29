package com.project.to_do.extensions

import com.project.to_do.debug
import com.project.to_do.release
import com.project.to_do.storage.DebugSigningProperties
import com.project.to_do.storage.ReleaseSigningProperties
import org.gradle.api.Project

fun Project.getSigningProperties(signingName: String) = when (signingName) {
    debug -> DebugSigningProperties(rootDir.path)
    release -> ReleaseSigningProperties(rootDir.path)
    else -> throw IllegalArgumentException("Unknown signingName = $signingName")
}