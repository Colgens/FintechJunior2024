plugins {
	java
	war
	jacoco
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.8.0"
	kotlin("plugin.spring") version "1.8.0"
}
jacoco {
	toolVersion = "0.8.7"
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it) {
				exclude("com/tbank/fintechjuniorspring/kudago/model/**")
			}
		})
	)
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "0.7".toBigDecimal()
			}
		}
	}
}

tasks.check {
	dependsOn(tasks.jacocoTestCoverageVerification)
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
	testImplementation("org.wiremock:wiremock-standalone:3.9.1")
	testImplementation("org.testcontainers:testcontainers:1.20.2")
	testImplementation("org.testcontainers:junit-jupiter:1.20.2")
	testImplementation("org.wiremock.integrations.testcontainers:wiremock-testcontainers-module:1.0-alpha-14")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.1")
	testImplementation("org.wiremock:wiremock:3.9.1")
	testImplementation ("org.assertj:assertj-core:3.24.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.4")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.1")
	implementation("com.tbank:execution-time-logging-starter:0.0.1-SNAPSHOT")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-aop:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-logging:3.3.4")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:3.3.4")
	implementation("org.projectlombok:lombok:1.18.34")
	annotationProcessor ("org.projectlombok:lombok:1.18.34")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}