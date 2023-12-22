plugins {
    `java-library`
    `maven-publish`

    eclipse
    idea
//    id("com.github.johnrengelman.shadow") version "8.1.1" // Shades and relocates dependencies, See https://imperceptiblethoughts.com/shadow/introduction/
}

group = "com.github.milkdrinkers"
version = "2.1.0"
description = ""

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_1_8.majorVersion)) // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
//    maven("https://oss.sonatype.org/content/repositories/snapshots") // Required for Spigots Bungeecord dependency
//    maven("https://oss.sonatype.org/content/repositories/central")  // Required for Spigots Bungeecord dependency

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
        content { includeGroup("me.clip") }
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.1.0") {
        isTransitive = false
    }
    annotationProcessor("org.jetbrains:annotations:24.1.0")

    compileOnly("net.kyori:adventure-api:4.14.0") {
//        isTransitive = false
    }
    compileOnly("net.kyori:adventure-text-minimessage:4.14.0") {
//        isTransitive = false
    }
    compileOnly("net.kyori:adventure-text-serializer-gson:4.14.0") {
//        isTransitive = false
    }
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.14.0") {
//        isTransitive = false
    }
    compileOnly("net.kyori:adventure-text-serializer-plain:4.14.0") {
//        isTransitive = false
    }
//    api("net.kyori:adventure-text-serializer-gson:4.14.0")
//    api("net.kyori:adventure-text-serializer-legacy:4.14.0")
//    api("net.kyori:adventure-text-serializer-plain:4.14.0") {
//        isTransitive = false
//    }

    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT") {
        isTransitive = false
    }
    // TODO Velocity support

    compileOnly("me.clip:placeholderapi:2.11.3") {
        isTransitive = false
    }

    testImplementation("net.kyori:adventure-api:4.14.0")
    testImplementation("net.kyori:adventure-text-minimessage:4.14.0")
//    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.58.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
        options.compilerArgs.addAll(arrayListOf("-Xlint:all", "-Xlint:-processing", "-Xdiags:verbose"))
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    test {
        useJUnitPlatform()
    }
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