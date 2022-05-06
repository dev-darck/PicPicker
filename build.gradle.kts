buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://maven.google.com") }
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
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

        dependencies {
            classpath("com.project.dependencies:dependencies:SNAPSHOT")
        }
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}