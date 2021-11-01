import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "me.redgm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.kotlindiscord.com/repository/maven-public/")
}

dependencies {
    implementation("dev.kord:kord-core:${properties["kordVersion"]}")
    implementation("com.kotlindiscord.kord.extensions:kord-extensions:${properties["kordExtensionVersion"]}")
    implementation("com.beust:klaxon:${properties["klaxonVersion"]}")
    implementation("com.charleskorn.kaml:kaml:${properties["kamlVersion"]}")
    implementation ("io.arrow-kt:arrow-core:${properties["arrowVersion"]}")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha10")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}