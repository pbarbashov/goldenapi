plugins {
    java
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}
val kafkaVersion = "2.6.1"
val reactorCoreVersion = "3.4.5"
val reactorKafkaVersion = "1.3.3"

subprojects {
    group = "ru.goldenapi"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.projectlombok:lombok")
        implementation("javax.validation:validation-api")
        implementation("com.fasterxml.jackson.core:jackson-databind")
        implementation("com.fasterxml.jackson.core:jackson-core")
        implementation("com.fasterxml.jackson.core:jackson-annotations")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("org.apache.kafka:kafka-clients:${kafkaVersion}")
        implementation("io.projectreactor:reactor-core:$reactorCoreVersion")
        implementation("io.projectreactor.kafka:reactor-kafka:${reactorKafkaVersion}")
        annotationProcessor("org.projectlombok:lombok")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:2.4.5")
        }
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }

}

tasks.forEach {
    it.onlyIf {
        false
    }
}
