package com.project.picpicker

import com.project.picpicker.dependency.helper.ApplicationPlugin
import com.project.picpicker.dependency.helper.LibraryPlugin
import com.project.picpicker.dependency.helper.addAppPlug
import com.project.picpicker.dependency.helper.addLibPlug

/** Default Plugin for library dependency */
val libraryPlugin: LibraryPlugin = addLibPlug(
    "com.android.library",
    "app-plugin",
)

/** Default Plugin for application dependency */
val applicationPlugin: ApplicationPlugin = addAppPlug(
    "com.android.application",
    "app-plugin",
)

const val hiltPlugin: String = "dagger.hilt.android.plugin"