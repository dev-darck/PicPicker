package com.project.picpicker

import com.project.picpicker.dependency.NameSpec
import com.project.picpicker.dependency.helper.*
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.getByType

private val Project.depToml: VersionCatalog
    get() {
        val catalogs = extensions.getByType<VersionCatalogsExtension>()
        return catalogs.named("dep")
    }

private fun VersionCatalog.safeFindLibrary(name: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(name).orElseGet { throw IllegalAccessException("Not found dependency name = $name") }

private fun VersionCatalog.safeFindVersion(name: String): VersionConstraint =
    findVersion(name).orElseGet { throw IllegalAccessException("Not found version name = $name") }

val Project.timber: NameSpec
    get() = depToml.safeFindLibrary("timber").impl

val Project.glideDeps: Dependency
    get() {
        val glide = depToml.safeFindLibrary("glide").impl
        val glideIntegration = depToml.safeFindLibrary("glide_integration").impl {
            exclude(group = "com.squareup.okhttp3", module = "okhttp")
        }
        val glideCompiler = depToml.safeFindLibrary("glide_compiler").kapt
        return deps(
            glide,
            glideIntegration,
            glideCompiler
        )
    }

val Project.jetpackComposeUiDeps: Dependency
    get() {
        val composeMaterial = depToml.safeFindLibrary("compose_material").impl
        val composeFoundation = depToml.safeFindLibrary("compose_foundation").impl
        val composeFoundationLayout = depToml.safeFindLibrary("compose_foundation_layout").impl
        val composeRuntime = depToml.safeFindLibrary("compose_runtime").impl
        val composeUi = depToml.safeFindLibrary("compose_ui").impl
        val composeTooling = depToml.safeFindLibrary("compose_tooling").impl
        val composeIconsCore = depToml.safeFindLibrary("compose_icons_core").impl
        val composeIconsExtended = depToml.safeFindLibrary("compose_icons_extended").impl
        val composeTestJunit4 = depToml.safeFindLibrary("compose_test_junit4").androidTest
        return deps(
            composeMaterial,
            composeFoundation,
            composeFoundationLayout,
            composeRuntime,
            composeUi,
            composeTooling,
            composeIconsCore,
            composeIconsExtended,
            composeTestJunit4,
        )
    }

val Project.composeVersion: String
    get() = depToml.safeFindVersion("compose").toString()

val Project.systemUiController: NameSpec
    get() = depToml.safeFindLibrary("system_ui_controller").impl

val Project.composePreviewDeps: Dependency
    get() {
        val uiTooling = depToml.safeFindLibrary("tooling_preview").impl
        val tooling = depToml.safeFindLibrary("tooling").debugImpl
        val poolingContainer = depToml.safeFindLibrary("pooling_container").debugImpl
        val customView = depToml.safeFindLibrary("custom_view").debugImpl
        return deps(
            uiTooling,
            tooling,
            poolingContainer,
            customView
        )
    }

val Project.jetpackComposeActivity: NameSpec
    get() = depToml.safeFindLibrary("activity_compose").impl

val Project.palette: NameSpec
    get() = depToml.safeFindLibrary("palette").impl

val Project.hiltNavigation: NameSpec
    get() = depToml.safeFindLibrary("hilt_navigation").impl


val Project.navigationDeps: Dependency
    get() {
        val navigation = depToml.safeFindLibrary("navigation").impl
        val navigationAnimation = depToml.safeFindLibrary("navigation_animation").impl
        val navigationMaterial = depToml.safeFindLibrary("navigation_material").impl
        return deps(
            navigation,
            navigationAnimation,
            navigationMaterial,
        )
    }

val Project.hiltDeps: Dependency
    get() {
        val hilt = depToml.safeFindLibrary("hilt").impl
        val hiltCompiler = depToml.safeFindLibrary("hilt_compiler").kapt
        return deps(
            hilt,
            hiltCompiler
        )
    }

val Project.leakCanary: NameSpec
    get() = depToml.safeFindLibrary("leakcanary").debugImpl

val Project.googleService: NameSpec
    get() = depToml.safeFindLibrary("google_service").impl

val Project.okHttp: NameSpec
    get() = depToml.safeFindLibrary("okhttp").impl

val Project.gson: NameSpec
    get() = depToml.safeFindLibrary("gson").impl

val Project.network: Dependency
    get() {
        val okHttpLogger = depToml.safeFindLibrary("logging_interceptor").impl
        val retrofit = depToml.safeFindLibrary("retrofit").impl
        val gson = depToml.safeFindLibrary("gson").impl
        return deps(
            okHttp,
            okHttpLogger,
            retrofit,
            gson,
        )
    }

val Project.pagerDeps: Dependency
    get() {
        val pager = depToml.safeFindLibrary("pager").impl
        val pagerIndicators = depToml.safeFindLibrary("pager_indicators").impl
        return deps(
            pager,
            pagerIndicators
        )
    }

val Project.base: Dependency
    get() {
        val core = depToml.safeFindLibrary("core").impl
        val appcompat = depToml.safeFindLibrary("appcompat").impl
        val material = depToml.safeFindLibrary("material").impl
        val jdk8 = depToml.safeFindLibrary("kotlin_stdlib_jdk8").impl
        val jupiter = depToml.safeFindLibrary("jupiter").test
        val junit = depToml.safeFindLibrary("junit").impl
        val espresso = depToml.safeFindLibrary("espresso").impl
        return deps(
            core,
            appcompat,
            material,
            jdk8,
            jupiter,
            junit,
            espresso,
            timber,
            leakCanary,
        )
    }