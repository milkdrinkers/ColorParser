import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import me.champeau.jmh.JMHPlugin
import me.champeau.jmh.JmhParameters

plugins {
    alias(libs.plugins.jmh)
    alias(libs.plugins.publisher)
}

plugins.withType<JMHPlugin> {
    extensions.configure(JmhParameters::class) {
        jmhVersion = libs.versions.jmh.get()
    }
    tasks.compileJmhJava {
        dependsOn(tasks.compileTestJava, tasks.processTestResources) // avoid implicit task dependencies
    }
    tasks.named(JMHPlugin.getJMH_TASK_COMPILE_GENERATED_CLASSES_NAME(), JavaCompile::class) {
        classpath += configurations.getByName(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME).incoming.files
    }
}

dependencies {
    jmh(rootProject.libs.bundles.jmh)
}

tasks {
    jmh {
        jmhVersion.set(rootProject.libs.jmh.core.get().version)

        benchmarkMode.set(listOf("SampleTime"))
        fork.set(2)
        warmupIterations.set(5)
        iterations.set(5)
        warmup.set("1s")
        timeOnIteration.set("1s")
        timeUnit.set("us")
        resultFormat.set("JSON")

        failOnError.set(false)
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.milkdrinkers",
        artifactId = "colorparser-common",
        version = version.toString().let { originalVersion ->
            if (!originalVersion.contains("-SNAPSHOT"))
                originalVersion
            else
                originalVersion.substringBeforeLast("-SNAPSHOT") + "-SNAPSHOT" // Force append just -SNAPSHOT if snapshot version
        }
    )

    pom {
        name.set(rootProject.name + "-Common")
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
    publishToMavenCentral()

    // Sign all publications
    signAllPublications()

    // Skip signing for local tasks
    tasks.withType<Sign>().configureEach { onlyIf { !gradle.taskGraph.allTasks.any { it is PublishToMavenLocal } } }
}