buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://maven.google.com") }
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("com.project.dependencies:dependencies:SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://maven.google.com") }
    }
}

subprojects {
    buildscript {
        repositories {
            google()
            mavenCentral()
            maven { setUrl("https://maven.google.com") }
        }
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}