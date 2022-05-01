package com.project.to_do.plugins.options

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.project.to_do.debug
import com.project.to_do.extensions.getSigningProperties
import com.project.to_do.extensions.setUp
import com.project.to_do.helper.VersionHelper
import com.project.to_do.release
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.applicationOptions() = extensions.getByType<BaseAppModuleExtension>().run {
    val version = VersionHelper(project.rootDir.path)
    repositories {
        google()
        mavenCentral()
    }

    signingConfigs {
        create(release) {
            setUp(getSigningProperties(this.name))
        }
        getByName(debug) {
            setUp(getSigningProperties(this.name))
        }
    }

    defaultConfig {
        applicationId = "com.project.to_do"
        minSdk = 21
        targetSdk = 32
        compileSdk = 32
        versionCode = version.versionCode()
        versionName = version.versionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        findByName(release)?.apply {
            signingConfig = signingConfigs.getByName(this.name)
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        findByName(debug)?.apply {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<KotlinCompile<KotlinJvmOptions>> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    }
}