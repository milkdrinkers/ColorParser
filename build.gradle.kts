import java.time.Instant

plugins {
    `java-library`
    alias(libs.plugins.shadow) apply false

    eclipse
    idea
}

applyCustomVersion()

tasks {
    jar {
        enabled = false
    }
}

subprojects {
    apply(plugin = "java-library")

    project.version = rootProject.version
    project.description = rootProject.description

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots") // Required for Spigots Bungeecord dependency
        maven("https://oss.sonatype.org/content/repositories/central") // Required for Spigots Bungeecord dependency
        maven("https://maven.fabricmc.net/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
            content { includeGroup("me.clip") }
        }
    }

    dependencies {
        compileOnly(rootProject.libs.annotations)
        annotationProcessor(rootProject.libs.annotations)

        compileOnly(rootProject.libs.bundles.adventure)

        testImplementation(rootProject.libs.annotations)
        testImplementation(platform(rootProject.libs.junit.bom))
        testImplementation(rootProject.libs.bundles.junit)
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        withJavadocJar()
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.compilerArgs.addAll(arrayListOf("-Xlint:all", "-Xlint:-processing", "-Xdiags:verbose"))
            options.release.set(8)
        }

        javadoc {
            isFailOnError = false
            val options = options as StandardJavadocDocletOptions
            options.encoding = Charsets.UTF_8.name()
            options.overview = "src/main/javadoc/overview.html"
            options.windowTitle = "${rootProject.name} Javadoc"
            options.tags("apiNote:a:API Note:", "implNote:a:Implementation Note:", "implSpec:a:Implementation Requirements:")
            options.addStringOption("Xdoclint:none", "-quiet")
            options.use()
        }

        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }

        test {
            useJUnitPlatform()
            failFast = false
        }
    }
}

fun applyCustomVersion() {
    // Apply custom version arg or append snapshot version
    val ver = properties["altVer"]?.toString() ?: "${rootProject.version}-SNAPSHOT.${Instant.now().epochSecond}"

    // Strip prefixed "v" from version tag
    rootProject.version = (if (ver.first().equals('v', true)) ver.substring(1) else ver.uppercase()).uppercase()
}