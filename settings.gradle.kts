pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.9.0")
}

rootProject.name = "ColorParser"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "common",
)