plugins {
    id("java")
    id("maven-publish")
}

group = "net.thenextlvl.incidents"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jspecify:jspecify:1.0.0")
    compileOnly("org.jetbrains:annotations:26.0.2-1")
}

tasks.withType<Javadoc>().configureEach {
    val options = options as StandardJavadocDocletOptions
    options.tags("apiNote:a:API Note:", "implSpec:a:Implementation Requirements:")
}

publishing {
    publications.create<MavenPublication>("maven") {
        artifactId = "incidents-io"
        groupId = "net.thenextlvl"
        // pom.url.set("https://thenextlvl.net/docs/incidents-io") // todo: add documentation
        pom.scm {
            val repository = "TheNextLvl-net/incidents-io"
            url.set("https://github.com/$repository")
            connection.set("scm:git:git://github.com/$repository.git")
            developerConnection.set("scm:git:ssh://github.com/$repository.git")
        }
        from(components["java"])
    }
    repositories.maven {
        val branch = if (version.toString().contains("-pre")) "snapshots" else "releases"
        url = uri("https://repo.thenextlvl.net/$branch")
        credentials {
            username = System.getenv("REPOSITORY_USER")
            password = System.getenv("REPOSITORY_TOKEN")
        }
    }
}