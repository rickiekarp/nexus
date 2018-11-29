plugins {
    id("org.gradle.java.experimental-jigsaw") version "0.1.1"
    application
}

val minorVersion = 0

apply {
    plugin("java")
}

//javaModule.name = "sha1pass"
application {
    applicationName = "SHA1Pass"
    mainClassName="net.rickiekarp.sha1pass.MainApp"
    version = "1.0-SNAPSHOT"
    group = "net.rickiekarp.sha1pass"
}

javaModule {
    setName("sha1pass")
}


dependencies {
    implementation("org.openjfx:javafx-controls:11:linux")
    implementation("org.openjfx:javafx-graphics:11:linux")
    implementation("org.openjfx:javafx-base:11:linux")

    compile(project(":core"))
}

//build {
//    println 'Building ' + project.name
//
//    ext {
//        if (project.hasProperty('privateBuildDate')) {
//            privateBuildDate = project.property('privateBuildDate')
//        } else {
//            privateBuildDate = new Date().format('yyMMddHHmm')
//        }
//
//        if (project.hasProperty('publicVersion')) {
//            publicVersion = project.property('publicVersion')
//        } else {
//            if (minorVersion > 0) {
//                publicVersion = new Date().format('yy.MM') + '.' + minorVersion
//            } else {
//                publicVersion = new Date().format('yy.MM')
//            }
//        }
//    }
//
//    jar {
//        baseName = 'sha1pass'
//        libsDirName = new File(rootProject.projectDir, "build/app")
//
//        manifest {
//            attributes 'Version': build.ext.publicVersion
//            attributes 'Build-Time': build.ext.privateBuildDate
//            attributes 'Main-Class': 'net.rickiekarp.sha1pass.MainApp'
//        }
//
//        // Include dependent libraries in archive.
////        from {
////            configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
////        }
//    }
//}