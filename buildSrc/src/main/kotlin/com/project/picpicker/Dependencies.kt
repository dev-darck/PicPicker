package com.project.picpicker

import com.project.picpicker.LibsVersion.accompanistNavigationVersion
import com.project.picpicker.LibsVersion.activityComposeVersion
import com.project.picpicker.LibsVersion.composeVersion
import com.project.picpicker.LibsVersion.customViewPoolingcontainerVersion
import com.project.picpicker.LibsVersion.customViewVersion
import com.project.picpicker.LibsVersion.hiltNavigationComposeVersion
import com.project.picpicker.LibsVersion.hiltVersion
import com.project.picpicker.LibsVersion.kotlinVersion
import com.project.picpicker.LibsVersion.navigationComposeVersion
import com.project.picpicker.LibsVersion.systemUiControllerVersion
import com.project.picpicker.LibsVersion.timberVersion
import com.project.picpicker.LibsVersion.uiToolingVersion
import com.project.picpicker.dependency.helper.androidTest
import com.project.picpicker.dependency.helper.debugImpl
import com.project.picpicker.dependency.helper.impl
import com.project.picpicker.dependency.helper.kapt

object Dependency {
    val composMaterial = "androidx.compose.material:material:$composeVersion".impl
    val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion".impl
    val composeFoundationLayout =
        "androidx.compose.foundation:foundation-layout:$composeVersion".impl
    val composeRuntime = "androidx.compose.runtime:runtime:$composeVersion".impl

    val jetpackComposeUi = arrayOf(
        "androidx.compose.ui:ui:$composeVersion".impl,
        "androidx.compose.ui:ui-tooling:$composeVersion".impl,
        composeFoundation,
        composMaterial,
        composeFoundationLayout,
        composeRuntime,
        "androidx.compose.material:material-icons-core:$composeVersion".impl,
        "androidx.compose.material:material-icons-extended:$composeVersion".impl,
        "androidx.compose.ui:ui-test-junit4:$composeVersion".androidTest
    )

    val systemuiController =
        "com.google.accompanist:accompanist-systemuicontroller:$systemUiControllerVersion".impl

    val composePreview = arrayOf(
        "androidx.compose.ui:ui-tooling-preview:$uiToolingVersion".impl,
        "androidx.compose.ui:ui-tooling:$uiToolingVersion".debugImpl,
        "androidx.customview:customview-poolingcontainer:$customViewPoolingcontainerVersion".debugImpl,
        "androidx.customview:customview:$customViewVersion".debugImpl,
    )

    val jetpackComposeActivity = "androidx.activity:activity-compose:$activityComposeVersion".impl

    val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion".impl

    val navigation = arrayOf(
        "androidx.navigation:navigation-compose:$navigationComposeVersion".impl,
        "com.google.accompanist:accompanist-navigation-animation:$accompanistNavigationVersion".impl,
        "com.google.accompanist:accompanist-navigation-material:$accompanistNavigationVersion".impl,
        hiltNavigation
    )

    val hilt = arrayOf(
        "com.google.dagger:hilt-android:$hiltVersion".impl,
        "com.google.dagger:hilt-android-compiler:$hiltVersion".kapt,
    )

    val timber = "com.jakewharton.timber:timber:$timberVersion".impl

    val baseDependency = arrayOf(
        "androidx.core:core-ktx:1.7.0".impl,
        "androidx.appcompat:appcompat:1.4.1".impl,
        "com.google.android.material:material:1.5.0".impl,
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion".impl,
        "junit:junit:4.13.2".impl,
        "androidx.test.ext:junit:1.1.3".impl,
        "androidx.test.espresso:espresso-core:3.4.0".impl,
        timber
    )
}

