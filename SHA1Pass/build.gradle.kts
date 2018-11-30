import java.util.Date
import java.text.SimpleDateFormat

plugins {
    id("org.gradle.java.experimental-jigsaw") version "0.1.1"
    application
}

apply {
    plugin("java")
}

application {
    applicationName = "SHA1Pass"
    mainClassName="net.rickiekarp.sha1pass.MainApp"
    group = "net.rickiekarp.sha1pass"
    version = "1.0-SNAPSHOT"
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

tasks {
    withType<Jar> {
        println("Building " + project.name)

        val minorVersion = 0

        val publicVersion = if (minorVersion > 0) {
            SimpleDateFormat("yy.MM").format(Date()) + '.' + minorVersion
        } else {
            SimpleDateFormat("yy.MM").format(Date())
        }

        manifest {
            attributes["Main-Class"] = application.mainClassName
            attributes["Build-Time"] = SimpleDateFormat("yyMMddHHmm").format(Date())
            attributes["Version"] = publicVersion
        }

        archiveName = "${javaModule.geName()}-$publicVersion.jar"
    }
}