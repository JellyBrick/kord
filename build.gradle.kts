plugins {
    org.jetbrains.dokka // for dokkaHtmlMultiModule task
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
}

group = Library.group
version = libraryVersion
