val keycloak_version = "13.0.0"


dependencies {
    implementation(project(":commons"))
    compileOnly("org.keycloak:keycloak-server-spi:${keycloak_version}")
    compileOnly("org.keycloak:keycloak-server-spi-private:${keycloak_version}")
    compileOnly("org.keycloak:keycloak-services:${keycloak_version}")
}


tasks.withType<Jar> {
    configurations["runtimeClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile)).exclude("META-INF/*.SF","META-INF/*.DSA","META-INF/*.RSA")
    }
}