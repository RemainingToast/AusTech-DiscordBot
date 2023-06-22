plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.austechmc"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.2")
    implementation("org.projectlombok:lombok:1.18.26")
}

tasks.jar {
    dependsOn(tasks.shadowJar)
    manifest {
        attributes["Main-Class"] = "net.austechmc.discord.DiscordBot"
    }
}