dependencies {
    implementation(project(":commons"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
