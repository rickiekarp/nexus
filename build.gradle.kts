import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openjfx.gradle.JavaFXOptions

buildscript {
    project.apply {
        from("module-base/base.gradle.kts")
        from("module-base/libs.gradle.kts")
        from("module-base/ui.gradle.kts")
    }

    repositories {
        mavenCentral()
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra.get("kotlinVersion")}")
        classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:${rootProject.extra.get("spotbugsPluginVersion")}")
    }
}

plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.7"
}

allprojects {
    apply {
        plugin("java")
        plugin("kotlin")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${rootProject.extra.get("kotlinVersion")}")
        testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra.get("junitVersion")}")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        setDestinationDir(File("$projectDir/build", "classes/java/main"))
    }
}

subprojects {
    apply {
        plugin("java-library")
        plugin("org.openjfx.javafxplugin")
    }

    configure<JavaFXOptions> {
        version = rootProject.extra.get("javafxVersion") as String
        modules("javafx.controls")
    }


//    apply plugin: 'com.github.spotbugs'
//    spotbugs {
//        reportsDir = file("$project.buildDir/reports/findbugs")
//        reportLevel = "high"
//        ignoreFailures = true
//    }

}

tasks.withType<Wrapper> {
    gradleVersion = rootProject.extra.get("gradleVersion") as String
}