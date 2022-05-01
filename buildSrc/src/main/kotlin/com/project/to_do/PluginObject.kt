package com.project.to_do

import com.project.to_do.dependency.helper.ApplicationPlugin
import com.project.to_do.dependency.helper.LibraryPlugin
import com.project.to_do.dependency.helper.addAppPlug
import com.project.to_do.dependency.helper.addLibPlug

val libraryPlugin : LibraryPlugin = addLibPlug(
    "com.android.library",
    "app-plugin"
)

val applicationPlugin : ApplicationPlugin = addAppPlug(
    "com.android.application",
    "app-plugin",
)