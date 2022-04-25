package com.project.to_do.plugins

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.plugins.AppPlugin
import com.project.to_do.helper.VersionHelper
import com.project.to_do.tasks.AutoInc
import java.util.*
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AppModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.all {
                addCommonPlugins()
                when (this) {
                    is AppPlugin -> {
                        autoInc()
                        addAndroidLibrarySection(false)
                        dependency()
                        appApp()
                    }
                    is LibraryPlugin -> {
                        addAndroidLibrarySection(true)
                        dependency()
                    }
                }
            }
        }
    }
}

private fun Project.addCommonPlugins() {
    plugins.apply("kotlin-android")
}

private fun Project.appApp() {
    plugins.apply("kotlin-kapt")
    plugins.apply("org.jetbrains.kotlin.android")
}

private fun Project.autoInc() {
    tasks.register("autoInc", AutoInc::class.java)
}

//переделать!
private fun Project.addAndroidLibrarySection(isLib: Boolean) = extensions.getByType<BaseExtension>().run {
    val version = VersionHelper(project.rootDir.path)
    repositories {
        google()
        mavenCentral()
    }

    defaultConfig {
        if (!isLib) {
            applicationId = "com.project.to_do"
        }
        minSdk = 21
        targetSdk = 32
        compileSdkVersion(32)
        versionCode = version.versionCode()
        versionName = version.versionName()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        signingConfigs {
            create("release") {
                val keystoreProperties = Properties()
                val keystorePropsFile = file("keystore/keystore_config")

                if (keystorePropsFile.exists()) {
                    keystoreProperties.apply {
                        load(keystorePropsFile.reader())
                    }
                    storeFile = file(keystoreProperties.getProperty("storeFile"))
                    storePassword = keystoreProperties.getProperty("storePassword")
                    keyAlias = keystoreProperties.getProperty("keyAlias")
                    keyPassword = keystoreProperties.getProperty("keyPassword")
                } else {
                    storeFile = file("keystore/to-do.keystore")
                    storePassword = System.getenv("KEYSTORE_PASSWORD")
                    keyAlias = System.getenv("RELEASE_SIGN_KEY_ALIAS")
                    keyPassword = System.getenv("RELEASE_SIGN_KEY_PASSWORD")
                }
            }
        }

        buildTypes {
            findByName("release")?.apply {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            findByName("debug")?.apply {
                isDebuggable = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        project.tasks.withType<KotlinCompile<KotlinJvmOptions>> {
            kotlinOptions.jvmTarget = "11"
        }
    }

private fun Project.dependency() {
    dependencies {
        add("implementation", "androidx.core:core-ktx:1.7.0")
        add("implementation", "androidx.appcompat:appcompat:1.4.1")
        add("implementation", "com.google.android.material:material:1.5.0")
        add("implementation", "junit:junit:4.13.2")
        add("implementation", "androidx.test.ext:junit:1.1.3")
        add("implementation", "androidx.test.espresso:espresso-core:3.4.0")
    }
}
