plugins {
	java
	jacoco
	id("org.sonarqube") version "6.0.1.5171"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"

val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"
val junitJupiterVersion = "5.9.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// === Application Dependencies === Spring Boot Starters
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// === Compile-Only Dependencies ===
	compileOnly("org.projectlombok:lombok")

	// === Annotation Processors ===
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	// === Development Tools ===
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// === Testing Dependencies ===
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
	testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
	testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
	testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")

	// === Test Runtime Dependencies ===
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register<Test>("unitTest") {
	description = "Runs unit tests."
	group = "verification"

	filter {
		excludeTestsMatching("*FunctionalTest")
	}
}

tasks.register<Test>("functionalTest") {
	description = "Runs functional tests."
	group = "verification"

	filter {
		includeTestsMatching("*FunctionalTest")
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}

tasks.test {
	filter{
		excludeTestsMatching("*FunctionalTest")
	}
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		html.required = true
		xml.required = true
	}
}

sonar {
	properties {
		property("sonar.projectKey", "almerazka_advprog-eshop")
		property("sonar.organization", "almerazka")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.gradle.skipCompile", "true")
		property("sonar.java.coveragePlugin", "jacoco")
		property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
	}
}