import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	id("com.palantir.docker")
}

group = "nu.westlin.microservices"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "Hoxton.RELEASE"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-zuul")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks {

    bootJar {
        manifest {
            attributes("Multi-Release" to true)
        }

/*
        archiveBaseName.set(appName)
        archiveVersion.set(appVer)
*/
        archiveBaseName.set("gateway-service")
        archiveVersion.set("0.1")

        if (project.hasProperty("archiveName")) {
            archiveFileName.set(project.properties["archiveName"] as String)
        }
    }

	docker {
        val build = build.get()
        val bootJar = bootJar.get()
        //val dockerImageName = "${project.group}/$appName"
        val dockerImageName = "${project.group}/gateway-service"

        dependsOn(build)

        name = "$dockerImageName:latest"
        //tag("current", "$dockerImageName:$appVer")
        tag("current", "$dockerImageName:0.1")
        tag("latest", "$dockerImageName:latest")
        files(bootJar.archiveFile)
        setDockerfile(file("$projectDir/src/main/docker/Dockerfile"))
        buildArgs(
            mapOf(
                "JAR_FILE" to bootJar.archiveFileName.get()
            )
        )
        pull(true)
    }
}
