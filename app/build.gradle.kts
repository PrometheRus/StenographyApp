plugins {
    application
    kotlin("jvm") version "1.8.21"
    id("org.openjfx.javafxplugin") version ("0.0.13")
    id("org.javamodularity.moduleplugin") version ("1.8.12")

}

repositories {
    mavenCentral()
}

javafx {
    version = "18.0.2"
    modules = mutableListOf("javafx.controls", "javafx.fxml")
}

val junitVersion = "5.9.1"

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClass.set("stenography.StenographyApp")
    mainModule.set("stenography")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(18)
}