deployer {
    release {
        version.set("${rootProject.version}")
        description.set(rootProject.description.orEmpty())
    }

    projectInfo {
        groupId = "io.github.milkdrinkers"
        artifactId = "colorparser"
        version = "${rootProject.version}"

        name = rootProject.name
        description = rootProject.description.orEmpty()
        url = "https://github.com/milkdrinkers/ColorParser"

        scm {
            connection = "scm:git:git://github.com/milkdrinkers/ColorParser.git"
            developerConnection = "scm:git:ssh://github.com:milkdrinkers/ColorParser.git"
            url = "https://github.com/milkdrinkers/ColorParser"
        }

        license("GNU General Public License Version 3", "https://www.gnu.org/licenses/gpl-3.0.en.html#license-text")

        developer({
            name.set("darksaid98")
            email.set("darksaid9889@gmail.com")
            url.set("https://github.com/darksaid98")
            organization.set("Milkdrinkers")
        })
    }

    content {
        component {
            fromJava()
        }
    }

    centralPortalSpec {
        auth.user.set(secret("MAVEN_USERNAME"))
        auth.password.set(secret("MAVEN_PASSWORD"))
    }

    signing {
        key.set(secret("GPG_KEY"))
        password.set(secret("GPG_PASSWORD"))
    }
}
