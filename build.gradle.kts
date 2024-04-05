import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"

    id("org.openapi.generator") version "7.2.0"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDirs("$buildDir/openapi")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // add springdoc-openapi dependencies
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Generate Stub
openApiGenerate {
    generatorName.set("kotlin-spring")
    id.set("openapi-tutorial")
    inputSpec.set("$rootDir/src/main/resources/yaml/user.yaml")
    outputDir.set("$buildDir/openapi")
    packageName.set("com.example.contracts")
    generateApiDocumentation.set(true) // API 문서 생성

    apiPackage.set("com.contracts.user")
    modelPackage.set("com.contracts.user")


    configOptions.set(
        mapOf(
            "gradleBuildFile" to "false", // gradle 파일 생성
            "useTags" to "true", // tag를 사용해 인터페이스/컨트롤러 이름 생성
            "delegatePattern" to "true", // delegatePattern 사용해 컨트롤러 정의

            "documentationProvider" to "springdoc", // OpenAPI documentation provider.
            "annotationLibrary" to "swagger2", // springdoc 사용 시 활용
            "useSwaggerUI" to "true", // Open the OpenApi specification in swagger-ui

            "useSpringBoot3" to "true", // spring-boot 3 사용 (jakarta annotation 사용)
            "interfaceOnly" to "true", // interface만 생성
            "useBeanValidation" to "false", // bean validation 사용X (jarkata validation 사용 X)
            "exceptionHandler" to "false", // exception handler 사용X
        )
    )
}
