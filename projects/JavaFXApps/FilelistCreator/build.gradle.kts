import java.util.Date
import java.text.SimpleDateFormat

plugins {
    application
}

apply {
    plugin("java")
}

application {
    applicationName = "Filelist Creator"
    mainClassName="net.rickiekarp.flc.MainApp"
    group = "net.rickiekarp.flc"
    version = "1.0-SNAPSHOT"
}

dependencies {
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

        archiveName = "flc-$publicVersion.jar"
    }
}