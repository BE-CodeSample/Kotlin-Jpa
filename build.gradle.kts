import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.4.1"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	kotlin("plugin.jpa") version "1.4.21"
}

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-gradle-plugin:2.4.1")
	implementation("io.spring.gradle:dependency-management-plugin:0.6.0.RELEASE")
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

	group = "sample.jpa"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_11

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}
	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.3")
		}
	}
	repositories {
		mavenCentral()
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude("junit")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
project(":application") {
	val bootJar: BootJar by tasks
	bootJar.archiveFileName.set("yourssu-timetable-application.jar")
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("io.springfox:springfox-boot-starter:3.0.0")
		implementation(project(":domain"))
	}
}

project(":domain") {
	val jar: Jar by tasks
	val bootJar: BootJar by tasks
	bootJar.enabled = false
	jar.enabled = true
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		runtimeOnly("com.h2database:h2")
		runtimeOnly("mysql:mysql-connector-java")
	}
}