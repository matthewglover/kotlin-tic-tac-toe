
rootProject.name = "TicTacToe"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        val kotlinVersion: String by settings
        val detektVersion: String by settings
        val ktlintVersion: String by settings
        val dependencyVersionsVersion: String by settings
        val dependencyCheckVersion: String by settings

        // Kotlin
        kotlin("jvm") version kotlinVersion

        // Static Analysis
        id("io.gitlab.arturbosch.detekt") version detektVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion

        // Dependency versions
        id("com.github.ben-manes.versions") version dependencyVersionsVersion
        id("org.owasp.dependencycheck") version dependencyCheckVersion
    }
}
