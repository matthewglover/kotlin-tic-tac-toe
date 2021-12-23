import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN


plugins {
    kotlin("jvm")
    application

    // Dependency management
    id("com.github.ben-manes.versions")
    id("org.owasp.dependencycheck")

    // Static Analysis
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "digital.fortisgreen.kotlin.tictactoe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    val arrowVersion: String by project
    val detektVersion: String by project
    val junitVersion: String by project
    val mockkVersion: String by project

    // Utility libraries
    implementation("io.arrow-kt:arrow-core:$arrowVersion")

    // Test
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")

    // Plugin configuration
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        // NOTE: unrestricted-builder-inference flag will be default in kotlin 1.6
        // See: https://kotlinlang.org/docs/whatsnew1530.html#eliminating-builder-inference-restrictions
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xself-upper-bound-inference", "-progressive")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// KtLint config
ktlint {
    reporters {
        reporter(PLAIN)
        reporter(HTML)
    }
    filter {
        exclude("**/generated/**")
        exclude("**/generated-test-sources/**")
    }
}

// Detekt config
detekt {
    reports {
        xml {
            enabled = false
        }
        txt {
            enabled = false
        }
        html {
            enabled = true
            destination = file("build/reports/detekt/detekt.html")
        }
    }
}

// Dependency versions config
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    gradleReleaseChannel = "current"
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
