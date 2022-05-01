package com.project.picpicker.extensions

import com.android.build.api.dsl.ApkSigningConfig
import com.project.picpicker.storage.SigningProperties

fun ApkSigningConfig.setUp(signingProperties: SigningProperties) {
    storeFile = signingProperties.storeFile
    storePassword = signingProperties.storePassword
    keyAlias = signingProperties.keyAlias
    keyPassword = signingProperties.keyPassword
}