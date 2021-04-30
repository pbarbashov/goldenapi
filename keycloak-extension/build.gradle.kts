val keycloak_version = "12.0.4"


dependencies {
    implementation(project(":commons"))
    implementation("org.keycloak:keycloak-server-spi:${keycloak_version}")
    implementation("org.keycloak:keycloak-server-spi-private:${keycloak_version}")
    implementation("org.keycloak:keycloak-services:${keycloak_version}")
}


tasks.withType<Jar> {
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}