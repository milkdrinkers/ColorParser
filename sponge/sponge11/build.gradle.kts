import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar

plugins {
    alias(libs.plugins.publisher)
}

dependencies {
    api(rootProject.projects.common)

    compileOnly(rootProject.libs.bundles.adventure)
    compileOnly(rootProject.libs.sponge.api11) {
        exclude("configurate-core")
        exclude("configurate-hocon")
        exclude("configurate-gson")
        exclude("configurate-yaml")
    }
}

tasks {
    compileJava {
        options.release.set(21)
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.milkdrinkers",
        artifactId = "colorparser-sponge11",
        version = version.toString().let { originalVersion ->
            if (!originalVersion.contains("-SNAPSHOT"))
                originalVersion
            else
                originalVersion.substringBeforeLast("-SNAPSHOT") + "-SNAPSHOT" // Force append just -SNAPSHOT if snapshot version
        }
    )

    pom {
        name.set(rootProject.name + "-Sponge11")
        description.set(rootProject.description.orEmpty())
        url.set("https://github.com/milkdrinkers/ColorParser")
        inceptionYear.set("2025")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("darksaid98")
                name.set("darksaid98")
                url.set("https://github.com/darksaid98")
                organization.set("Milkdrinkers")
            }
        }

        scm {
            url.set("https://github.com/milkdrinkers/ColorParser")
            connection.set("scm:git:git://github.com/milkdrinkers/ColorParser.git")
            developerConnection.set("scm:git:ssh://github.com:milkdrinkers/ColorParser.git")
        }
    }

    configure(JavaLibrary(
        javadocJar = JavadocJar.None(), // We want to use our own javadoc jar
    ))

    // Publish to Maven Central
    publishToMavenCentral(automaticRelease = true)

    // Sign all publications
    signAllPublications()

    // Skip signing for local tasks
    tasks.withType<Sign>().configureEach { onlyIf { !gradle.taskGraph.allTasks.any { it is PublishToMavenLocal } } }
}