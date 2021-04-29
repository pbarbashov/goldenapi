val keycloak_version = "12.0.4"
val kafkaVersion = "2.6.1"
val reactorCoreVersion = "3.4.5"
val reactorKafkaVersion = "1.3.3"

dependencies {
    implementation("org.keycloak:keycloak-server-spi:${keycloak_version}")
    implementation("org.keycloak:keycloak-server-spi-private:${keycloak_version}")
    implementation("org.keycloak:keycloak-services:${keycloak_version}")
    implementation("org.apache.kafka:kafka-clients:${kafkaVersion}")
    implementation( "io.projectreactor:reactor-core:$reactorCoreVersion")
    implementation("io.projectreactor.kafka:reactor-kafka:${reactorKafkaVersion}")
}


tasks.withType<Jar> {
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}