plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

gradlePlugin {
    plugins {
        register("app-plugin") {
            id = "app-plugin"
            implementationClass = "com.project.to_do.AppModulePlugin"
        }
    }
}

dependencies {
    implementation(kotlin("gradle-plugin", "1.6.21"))
    implementation(kotlin("android-extensions"))
    implementation("com.android.tools.build:gradle:7.1.3")
}