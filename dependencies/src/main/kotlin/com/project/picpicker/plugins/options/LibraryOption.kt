package com.project.picpicker.plugins.options

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.libraryOptions() = extensions.getByType<LibraryExtension>().run {
    repositories {
        google()
        mavenCentral()
    }

    defaultConfig {
        minSdk = 21
        targetSdk = 32
        compileSdk = 32
        consumerProguardFiles("proguard-rules.pro")
    }

    buildTypes {
        findByName("release")?.apply {
            isMinifyEnabled = false
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