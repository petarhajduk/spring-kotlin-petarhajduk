import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //id("org.jetbrains.kotlin.plugin.spring") version "1.7.0"
    id("org.springframework.boot") version "2.7.1" //defines version of Spring Boot
    id("io.spring.dependency-management") version "1.0.11.RELEASE" //Handles Spring Boot dependencies
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    kotlin("plugin.jpa") version "1.7.10"
    application
}

group = "me.petar"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

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
    testImplementation("org.mock-server:mockserver-spring-test-listener:5.11.2")


    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc") //Adds db functionality
    implementation("org.springframework.boot:spring-boot-starter-web") //Adds web functionality
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test") //Adds Spring Boot test
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.ninja-squad:springmockk:3.1.1") //Used for using Mockk with Spring

    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.springdoc:springdoc-openapi-hateoas:1.6.9")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

application {
    mainClass.set("MainKt")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:1.17.3")
    }
}