plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
    id("com.github.ben-manes.versions") version "0.42.0"
}

group = "com.project.dependencies"
version = "SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        setUrl("https://plugins.gradle.org/m2/")
    }
    google()
}

gradlePlugin {
    plugins {
        register("app-plugin") {
            id = "app-plugin"
            implementationClass = "com.project.picpicker.plugins.AppModulePlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation(kotlin("gradle-plugin", dep.versions.kotlinVersion.get()))
    implementation(kotlin("android-extensions"))
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.42.0")
}