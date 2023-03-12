import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer

plugins {
    java
    id("org.springframework.boot") version "2.7.7"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.icloud"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.clojars.org")
        name = "Clojars"
    }
}

extra["springCloudVersion"] = "2021.0.5"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.cloud:spring-cloud-function-context")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web")
    implementation("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    runtimeOnly("com.amazonaws:aws-lambda-java-log4j2:1.5.1")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.assemble {
    dependsOn("shadowJar")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("aws")
    dependencies {
        exclude(
            dependency("org.springframework.cloud:spring-cloud-function-web")
        )
    }
    // Required for Spring
    mergeServiceFiles()
    append("META-INF/spring.handlers")
    append("META-INF/spring.schemas")
    append("META-INF/spring.tooling")
    append("META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports")
    append("META-INF/spring/org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration.imports")
    transform(PropertiesFileTransformer::class.java) {
        paths = listOf("META-INF/spring.factories")
        mergeStrategy = "append"
    }
}
