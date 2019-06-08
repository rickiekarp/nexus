import java.util.Date
import java.text.SimpleDateFormat

plugins {
    application
}

apply {
    plugin("java")
}

application {
    applicationName = "Bot Manager"
    mainClassName="net.rickiekarp.botter.MainApp"
    group = "net.rickiekarp.botter"
    version = "1.0-SNAPSHOT"
}

dependencies {
    compile(project(":core"))
    compile(project(":BotLib"))
    implementation("org.json:json:${rootProject.extra.get("jsonVersion")}")
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

//        archiveName = "${javaModule.geName()}-$publicVersion.jar"
    }
}