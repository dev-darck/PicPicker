package com.project.to_do.storage

import java.io.File

class DebugSigningProperties(private val parentPath: String) : SigningProperties(parentPath) {
    override val storeFile: File
        get() = File(parentPath, getStringValue("SIGNING_STORE_FILE_PATH_DEBUG"))
    override val storePassword: String
        get() = getStringValue("SIGNING_STORE_PASSWORD_DEBUG")
    override val keyAlias: String
        get() = getStringValue("SIGNING_KEY_ALIAS_DEBUG")
    override val keyPassword: String
        get() = getStringValue("SIGNING_KEY_PASSWORD_DEBUG")
}