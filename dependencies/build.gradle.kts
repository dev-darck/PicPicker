plugins {
    `kotlin-dsl`
}

group = "com.project.dependencies"
version = "SNAPSHOT"

repositories {
    mavenCentral()
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
}