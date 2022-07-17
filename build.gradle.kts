import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //id("org.jetbrains.kotlin.plugin.spring") version "1.7.0"
    id("org.springframework.boot") version "2.7.1" //defines version of Spring Boot
    id("io.spring.dependency-management") version "1.0.11.RELEASE" //Handles Spring Boot dependencies
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    application
}

group = "me.petar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.springframework:spring-context:5.3.20")
    testImplementation("org.springframework:spring-test:5.3.20")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("io.mockk:mockk:1.12.4")

    implementation("org.springframework.boot:spring-boot-starter-web") //Adds web functionality
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") //Adds Spring Boot test
    testImplementation("com.ninja-squad:springmockk:3.1.1") //Used for using Mockk with Spring
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "15"
}

application {
    mainClass.set("MainKt")
}

tasks.withType<Test> {
    useJUnitPlatform()
}