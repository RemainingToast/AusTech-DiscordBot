plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.austechmc"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        setUrl("https://jitpack.io")
    }
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.11")
    implementation("org.projectlombok:lombok:1.18.26")
    implementation("com.github.koply.KCommando:jda-integration:5.1.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.jar {
    dependsOn(tasks.shadowJar)
    manifest {
        attributes["Main-Class"] = "net.austechmc.discord.DiscordBot"
    }
}