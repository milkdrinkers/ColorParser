plugins {
    `java-library`
    `maven-publish`

//    id("com.github.johnrengelman.shadow") version "8.1.1" // Shades and relocates dependencies, See https://imperceptiblethoughts.com/shadow/introduction/
}

group = "com.github.milkdrinkers"
version = "2.0.0"
description = ""

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17)) // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        content {
            includeGroup("org.bukkit")
            includeGroup("org.spigotmc")
        }
    }
    maven("https://oss.sonatype.org/content/repositories/snapshots") // Required for Spigots Bungeecord dependency
    maven("https://oss.sonatype.org/content/repositories/central")  // Required for Spigots Bungeecord dependency

//    maven("https://papermc.io/repo/repository/maven-public/")

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        content { includeGroup("me.clip") }
    }

//    maven("https://jitpack.io/") { content {} }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")

    api("net.kyori:adventure-api:4.14.0")
    api("net.kyori:adventure-text-minimessage:4.14.0")
    api("net.kyori:adventure-text-serializer-gson:4.14.0")
    api("net.kyori:adventure-text-serializer-legacy:4.14.0")
    api("net.kyori:adventure-text-serializer-plain:4.14.0")

    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
//    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    compileOnly("me.clip:placeholderapi:2.11.3")
}

tasks {
    /*build {
        dependsOn(shadowJar)
    }*/

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        sourceCompatibility = "${JavaVersion.VERSION_1_8}"
    }

    /*shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")

        // Shadow classes
        // helper function to relocate a package into our package
        fun reloc(originPkg: String, targetPkg: String) = relocate(originPkg, "${project.group}.colorparser.${targetPkg}")
    }*/
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = "colorparser"
            version = "${project.version}"

            from(components["java"])
        }
    }
}