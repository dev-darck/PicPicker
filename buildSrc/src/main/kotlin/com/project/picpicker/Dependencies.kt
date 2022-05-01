package com.project.picpicker

import com.project.picpicker.LibsVersion.hilt_version
import com.project.picpicker.dependency.helper.androidTest
import com.project.picpicker.dependency.helper.debugImpl
import com.project.picpicker.dependency.helper.impl
import com.project.picpicker.dependency.helper.kapt

object Dependency {
    val composMaterial = "androidx.compose.material:material:1.2.0-alpha08".impl
    val composeFoundation =  "androidx.compose.foundation:foundation:1.2.0-alpha08".impl
    val composeFoundationLayout =  "androidx.compose.foundation:foundation-layout:1.2.0-alpha08".impl

    val jetpackComposeUi = arrayOf(
        "androidx.compose.ui:ui:1.2.0-alpha08".impl,
        "androidx.compose.ui:ui-tooling:1.2.0-alpha08".impl,
        composeFoundation,
        composMaterial,
        composeFoundationLayout,
        "androidx.compose.material:material-icons-core:1.2.0-alpha08".impl,
        "androidx.compose.material:material-icons-extended:1.2.0-alpha08".impl,
        "androidx.compose.ui:ui-test-junit4:1.2.0-alpha08".androidTest
    )

    val composePreview = arrayOf(
        "androidx.compose.ui:ui-tooling-preview:1.1.1".impl,
        "androidx.compose.ui:ui-tooling:1.1.1".debugImpl,
    )

    val jetpackComposeActivity = "androidx.activity:activity-compose:1.3.1".impl
    val navigation = arrayOf(
        "androidx.navigation:navigation-compose:2.4.2".impl,
        "com.google.accompanist:accompanist-navigation-animation:0.23.1".impl,
        "com.google.accompanist:accompanist-navigation-material:0.23.1".impl,
        "androidx.hilt:hilt-navigation-compose:1.0.0".impl
    )
    val composeRuntime = "androidx.compose.runtime:runtime:1.2.0-alpha08".impl

    val hilt = arrayOf(
        "com.google.dagger:hilt-android:$hilt_version".impl,
        "com.google.dagger:hilt-android-compiler:$hilt_version".kapt,

        )

    val timber = "com.jakewharton.timber:timber:5.0.1".impl

    val baseDependency = arrayOf(
        "androidx.core:core-ktx:1.7.0".impl,
        "androidx.appcompat:appcompat:1.4.1".impl,
        "com.google.android.material:material:1.5.0".impl,
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.20".impl,
        "junit:junit:4.13.2".impl,
        "androidx.test.ext:junit:1.1.3".impl,
        "androidx.test.espresso:espresso-core:3.4.0".impl,
        timber
    )
}

