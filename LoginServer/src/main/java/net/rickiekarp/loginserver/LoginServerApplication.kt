package net.rickiekarp.loginserver

import net.rickiekarp.foundation.config.Config
import net.rickiekarp.foundation.config.ServerContext
import net.rickiekarp.foundation.logger.Log
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import java.io.File
import java.util.*

@SpringBootApplication
@ComponentScan(value = ["net.rickiekarp.foundation", "net.rickiekarp.loginserver"])
open class LoginServerApplication: SpringBootServletInitializer() {

    override fun configure(springApplicationBuilder: SpringApplicationBuilder): SpringApplicationBuilder {
        return springApplicationBuilder
                .sources(LoginServerApplication::class.java)
                .properties(getProperties())
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(LoginServerApplication::class.java)
            .sources(LoginServerApplication::class.java)
            .properties(getProperties())
            .run(*args)
}

fun getProperties(): Properties {
    loadConfiguration("LoginServer")
    ServerContext.serverVersion = evaluateServerVersion()

    Config.get().setupDirectory = setupPath()
    val props = Properties()
    props["spring.config.location"] = "file:${Config.get().setupDirectory}/"
    return props
}

/**
 * Creates the Config and loads the settings
 */
private fun loadConfiguration(applicationName: String) {
    val configBuilder = Config.ConfigBuilder().setApplicationIdentifier(applicationName)

    // set up Config
    Config.create(configBuilder)
}

fun setupPath(): String {
    return findSetupDirectory(Config.get().applicationIdentifier)
}


/**
 * Determines the setup directory given the name for this app.
 * In development mode this simply returns the given path.
 * In non-development it will search the current working directory of the user for the setup directory
 */
private fun findSetupDirectory(aSetupDirectoryName: String): String {
    if (ServerContext.developerEnvironment) {
        val workingDirectory = System.getProperty("user.dir")
        return "$workingDirectory.setup"
    } else {
        val directories = File(System.getProperty("user.dir")).listFiles().filter { it.isDirectory }
        for (directory in directories) {
            if (directory.name == "${Config.get().applicationIdentifier}.setup") {
                Log.DEBUG.trace("Setup directory ${directory.name} found in ${directory.path}")
                return "${System.getProperty("user.dir")}/${directory.name}"
            }
        }
        Log.DEBUG.error("Setup directory could not be found in current path ${System.getProperty("user.dir")}")
    }
    return aSetupDirectoryName
}

fun evaluateServerVersion() : String {
    val version = LoginServerApplication::class.java.`package`.implementationVersion
    // if the implementation version is null (because there is no manifest file),
    // it is assumed that the application is running in a development environment
    if (version == null) {
        Log.DEBUG.debug("Implementation version could not be found, assuming developer environment!")
        ServerContext.developerEnvironment = true
        return version
    }
    return version
}