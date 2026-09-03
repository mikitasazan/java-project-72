plugins {
    application
    id("com.gradleup.shadow") version "9.6.1"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:7.2.3")
    implementation("io.javalin:javalin-rendering-jte:7.2.3")
    implementation("gg.jte:jte:3.2.4")
    implementation("org.slf4j:slf4j-simple:2.0.18")
    implementation("com.zaxxer:HikariCP:7.1.0")
    implementation("com.h2database:h2:2.4.240")
    implementation("org.postgresql:postgresql:42.7.13")

    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
