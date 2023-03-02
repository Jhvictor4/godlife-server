import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion = "3.0.2"

plugins {
	id("org.springframework.boot") version "3.0.2" apply false
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.jpa") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
}

allprojects {
	group = "com.godlife"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "maven-publish")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "kotlin-spring")
	apply(plugin = "kotlin-jpa")
	apply(plugin = "org.jlleitschuh.gradle.ktlint")

	ktlint {
		disabledRules.set(setOf("no-wildcard-imports"))
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
		}

		dependencies {
			dependency("org.springframework.boot:spring-boot-starter-test:$springBootVersion")

			dependencySet("org.springdoc:1.6.9") {
				entry("springdoc-openapi-ui")
				entry("springdoc-openapi-kotlin")
			}
		}
	}

	dependencies {
		implementation(kotlin("stdlib-jdk8"))
		implementation(kotlin("reflect"))

		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		}

		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "17"
			freeCompilerArgs = listOf("-Xjsr305=strict")
		}
	}
}