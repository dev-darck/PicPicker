package com.project.picpicker.extensions

import com.android.build.api.dsl.ApkSigningConfig
import com.project.picpicker.managers.SigningPropertiesManager

fun ApkSigningConfig.setUp(signingPropertiesManager: SigningPropertiesManager) {
    storeFile = signingPropertiesManager.storeFile
    storePassword = signingPropertiesManager.storePassword
    keyAlias = signingPropertiesManager.keyAlias
    keyPassword = signingPropertiesManager.keyPassword
}