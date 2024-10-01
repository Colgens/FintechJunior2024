plugins {
	`java-library`
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	`maven-publish`
}

group = "com.tbank"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-tomcat:3.3.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-aop:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-logging:3.3.4")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<Jar>("jar") {
	enabled = true
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = false
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}
	repositories {
		maven {
			url = uri(layout.buildDirectory.dir("repo"))
		}
	}
}

