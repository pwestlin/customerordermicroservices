import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.50"
	id("com.palantir.docker") version "0.22.1"
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
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
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
        archiveBaseName.set("discovery-service")
        archiveVersion.set("0.1")

        if (project.hasProperty("archiveName")) {
            archiveFileName.set(project.properties["archiveName"] as String)
        }
    }

	docker {
        val build = build.get()
        val bootJar = bootJar.get()
        //val dockerImageName = "${project.group}/$appName"
        val dockerImageName = "${project.group}/discovery-service"

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
