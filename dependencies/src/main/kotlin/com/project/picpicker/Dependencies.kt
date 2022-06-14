package com.project.picpicker

import com.project.picpicker.LibsVersion.accompanistNavigationVersion
import com.project.picpicker.LibsVersion.activityComposeVersion
import com.project.picpicker.LibsVersion.appComponentVersion
import com.project.picpicker.LibsVersion.composeVersion
import com.project.picpicker.LibsVersion.coreVersion
import com.project.picpicker.LibsVersion.customViewPoolingcontainerVersion
import com.project.picpicker.LibsVersion.customViewVersion
import com.project.picpicker.LibsVersion.glideVersion
import com.project.picpicker.LibsVersion.hiltNavigationComposeVersion
import com.project.picpicker.LibsVersion.hiltVersion
import com.project.picpicker.LibsVersion.kotlinVersion
import com.project.picpicker.LibsVersion.leakcanaryVersion
import com.project.picpicker.LibsVersion.materialVersion
import com.project.picpicker.LibsVersion.navigationComposeVersion
import com.project.picpicker.LibsVersion.okhttpVersion
import com.project.picpicker.LibsVersion.pagerVersion
import com.project.picpicker.LibsVersion.paletteVersion
import com.project.picpicker.LibsVersion.retrofitVersion
import com.project.picpicker.LibsVersion.systemUiControllerVersion
import com.project.picpicker.LibsVersion.testVersion
import com.project.picpicker.LibsVersion.timberVersion
import com.project.picpicker.LibsVersion.uiToolingVersion
import com.project.picpicker.dependency.helper.*
import org.gradle.kotlin.dsl.exclude

object Dependency {
    val composMaterial = "androidx.compose.material:material:$composeVersion".impl
    val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion".impl
    val composeFoundationLayout =
        "androidx.compose.foundation:foundation-layout:$composeVersion".impl
    val composeRuntime = "androidx.compose.runtime:runtime:$composeVersion".impl

    val glideDeps = deps(
        "com.github.bumptech.glide:glide:$glideVersion".impl,
        "com.github.bumptech.glide:okhttp3-integration:$glideVersion".impl {
            exclude(group = "com.squareup.okhttp3", module = "okhttp")
        },
        "com.github.bumptech.glide:compiler:$glideVersion".kapt,
    )


    val jetpackComposeUiDeps = deps(
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

    val composePreviewDeps = deps(
        "androidx.compose.ui:ui-tooling-preview:$uiToolingVersion".impl,
        "androidx.compose.ui:ui-tooling:$uiToolingVersion".debugImpl,
        "androidx.customview:customview-poolingcontainer:$customViewPoolingcontainerVersion".debugImpl,
        "androidx.customview:customview:$customViewVersion".debugImpl,
    )

    val jetpackComposeActivity = "androidx.activity:activity-compose:$activityComposeVersion".impl
    val palette = "com.android.support:palette-v7:$paletteVersion".impl

    val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion".impl

    val navigationDeps = deps(
        "androidx.navigation:navigation-compose:$navigationComposeVersion".impl,
        "com.google.accompanist:accompanist-navigation-animation:$accompanistNavigationVersion".impl,
        "com.google.accompanist:accompanist-navigation-material:$accompanistNavigationVersion".impl,
        hiltNavigation
    )

    val hiltDeps = deps(
        "com.google.dagger:hilt-android:$hiltVersion".impl,
        "com.google.dagger:hilt-android-compiler:$hiltVersion".kapt,
    )

    val timber = "com.jakewharton.timber:timber:$timberVersion".impl
    val leakCanary = "com.squareup.leakcanary:leakcanary-android:$leakcanaryVersion".debugImpl

    val baseDependencyDeps = deps(
        "androidx.core:core-ktx:$coreVersion".impl,
        "androidx.appcompat:appcompat:$appComponentVersion".impl,
        "com.google.android.material:material:$materialVersion".impl,
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion".impl,
        "org.junit.jupiter:junit-jupiter-api:$testVersion".test,
        "androidx.test.ext:junit:1.1.3".impl,
        "androidx.test.espresso:espresso-core:3.4.0".impl,
        timber,
        leakCanary
    )
    val okHttpLogger = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion".impl
    val okHttp = "com.squareup.okhttp3:okhttp:$okhttpVersion".impl
    val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion".impl
    val gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion".impl

    val pagerDeps = deps(
        "com.google.accompanist:accompanist-pager:$pagerVersion".impl,
        "com.google.accompanist:accompanist-pager-indicators:$pagerVersion".impl,
    )
}

