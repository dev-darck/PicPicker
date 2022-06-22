rootProject.name = "dependencies"

dependencyResolutionManagement {
    versionCatalogs {
        create("dep") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}