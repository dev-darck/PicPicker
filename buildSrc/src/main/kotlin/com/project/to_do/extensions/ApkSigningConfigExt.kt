package com.project.to_do.extensions

import com.android.build.api.dsl.ApkSigningConfig
import com.project.to_do.storage.SigningProperties

fun ApkSigningConfig.setUp(signingProperties: SigningProperties) {
    storeFile = signingProperties.storeFile
    storePassword = signingProperties.storePassword
    keyAlias = signingProperties.keyAlias
    keyPassword = signingProperties.keyPassword
}