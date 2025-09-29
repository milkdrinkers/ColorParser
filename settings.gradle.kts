pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("1.0.0")
}

rootProject.name = "ColorParser"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "common",
    "bukkit",
    "paper",
    "velocity",
    "sponge:sponge8",
    "sponge:sponge9",
    "sponge:sponge10",
    "sponge:sponge11",
    "sponge:sponge12",
    "sponge:sponge13",
    "sponge:sponge14",
)
