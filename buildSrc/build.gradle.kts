plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    // Repo providing the Kord Gradle plugin
    maven("https://europe-west3-maven.pkg.dev/mik-music/kord")
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
}

dependencies {
    implementation(libs.bundles.pluginsForBuildSrc)
}
