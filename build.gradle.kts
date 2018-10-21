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
        classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:1.6.1")
    }
}

apply {
    plugin("java")
    plugin("kotlin")
}

plugins {
    java
    application
    idea
}

application {
    applicationName = "JavaFxApp"
    version = "1.0-SNAPSHOT"
    group = "net.rickiekarp.toolbox"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
}

allprojects {
    apply {
        plugin("java")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${rootProject.extra.get("kotlinVersion")}")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

//    compileKotlin {
//        kotlinOptions.jvmTarget = "1.8"
//        destinationDir = compileJava.destinationDir
//    }
//
//    compileTestKotlin {
//        kotlinOptions.jvmTarget = "1.8"
//    }
}

subprojects {
    apply {
        plugin("java-library")
    }

//    clean {
//        doFirst {
//            delete("$project.projectDir/out")
//        }
//    }

//    apply plugin: 'com.github.spotbugs'
//    spotbugs {
//        reportsDir = file("$project.buildDir/reports/findbugs")
//        reportLevel = "high"
//        ignoreFailures = true
//    }

}

//repositories {
//    mavenCentral()
//}
//
//wrapper {
//    gradleVersion = gradleWrapperVersion
//}