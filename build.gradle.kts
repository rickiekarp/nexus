import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    project.apply {
        from("module-base/base.gradle.kts")
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
}

application {
    applicationName = "JavaFxApp"
    version = "1.0-SNAPSHOT"
    group = "net.rickiekarp.toolbox"
}

allprojects {
    apply {
        plugin("java")
        plugin("kotlin")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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