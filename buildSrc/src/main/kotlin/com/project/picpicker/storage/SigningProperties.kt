package com.project.picpicker.storage

import java.io.File
import java.util.*

private const val KEYSTORE_CONFIG_PATH = "keystore/keystore_config"

abstract class SigningProperties(parentPath: String) {
    abstract val storeFile: File
    abstract val storePassword: String
    abstract val keyAlias: String
    abstract val keyPassword: String

    private val keystoreConfigFile = File(parentPath, KEYSTORE_CONFIG_PATH)
    private val keystoreProperties: Properties? = Properties().takeIf {
        keystoreConfigFile.exists()
    }?.apply {
        load(keystoreConfigFile.reader())
    }

    fun getStringValue(name: String): String =
        keystoreProperties?.getProperty(name) ?: System.getenv(name).orEmpty()

}
