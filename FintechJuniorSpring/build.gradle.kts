plugins {
	java
	war
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.8.0"
	kotlin("plugin.spring") version "1.8.0"
}

group = "com.tbank"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("com.tbank:execution-time-logging-starter:0.0.1-SNAPSHOT")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-aop:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-logging:3.3.4")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:3.3.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.4")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.1")
	implementation("org.projectlombok:lombok:1.18.34")
	annotationProcessor ("org.projectlombok:lombok:1.18.34")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}