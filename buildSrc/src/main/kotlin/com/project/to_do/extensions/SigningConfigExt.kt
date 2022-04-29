package com.project.to_do.extensions

import com.android.build.gradle.internal.dsl.SigningConfig
import com.project.to_do.storage.SigningProperties

fun SigningConfig.setUp(signingProperties: SigningProperties) {
    storeFile = signingProperties.storeFile
    storePassword = signingProperties.storePassword
    keyAlias = signingProperties.keyAlias
    keyPassword = signingProperties.keyPassword
}