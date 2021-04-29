plugins {
    java
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

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
