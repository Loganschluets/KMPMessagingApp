enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()

        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/icerock/moko/dev") }
        maven { url = uri("https://maven.jetpack.io") } // Add Jetpack.io repository
    }
}

rootProject.name = "NetworkingApp"
include(":androidApp")
include(":shared")