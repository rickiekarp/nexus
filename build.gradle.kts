group 'net.rickiekarp.appbox'

buildscript {
    apply from: 'module-base/base.gradle'

    repositories {
        mavenCentral()
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion" as Object
        classpath "gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:1.6.1"
    }
}

allprojects {
    apply plugin: 'java'
    apply plugin: 'kotlin'
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
    repositories {
        mavenCentral()
    }
    dependencies {
        compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion" as Object
        testCompile group: 'junit', name: 'junit', version: junit4Version
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        destinationDir = compileJava.destinationDir
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

subprojects {
    apply plugin: "java-library"

    clean {
        doFirst {
            delete "$project.projectDir/out" as Object
        }
    }

//    apply plugin: 'com.github.spotbugs'
//    spotbugs {
//        reportsDir = file("$project.buildDir/reports/findbugs")
//        reportLevel = "high"
//        ignoreFailures = true
//    }

}

repositories {
    mavenCentral()
}

wrapper {
    gradleVersion = gradleWrapperVersion
}