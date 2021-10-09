plugins {
    java
    `java-library`
    id("org.springframework.boot") version "2.5.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }

    dependencies {
        // lombok
        implementation("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        compileOnly("org.springframework.boot:spring-boot-devtools")

    }

    ext {
        set("apacheHttpClientVersion", "4.5.13")
    }
}

configure(subprojects.filter { it.name !in listOf<String>("shared") }) {

    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.2")
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        // mongo db
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive") // mongodb-driver-reactivestreams
        implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
        implementation("org.mongodb:mongodb-driver-sync") // tranditional mongo DB driver

        // blockHound
        implementation("io.projectreactor.tools:blockhound:1.0.3.RELEASE")
        testImplementation("io.projectreactor.tools:blockhound-junit-platform:1.0.6.RELEASE")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher") // 테스트 시 사용하려면 이 의존성 필요

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }

    tasks {
        "test"(Test::class) {
            useJUnitPlatform()
        }
    }
}

// subProjects settings for non-packing
configure(subprojects.filter { it.parent?.name in listOf { "something" } }) {
    tasks.bootJar {
        enabled = false
    }
    tasks.jar {
        enabled = false
    }
}

configurations.all {
    // SNAPSHOT 버전의 dependencies는 캐싱하지 않는다.
    resolutionStrategy.cacheChangingModulesFor(1, "seconds")
}
