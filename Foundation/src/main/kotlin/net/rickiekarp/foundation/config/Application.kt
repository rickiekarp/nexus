package net.rickiekarp.foundation.config

import net.rickiekarp.foundation.logger.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

open class Application {

    fun getProperties(clazz: Class<*>, applicationName: String): Properties {
        ServerContext.serverVersion = evaluateServerVersion(clazz.`package`.implementationVersion)

        val configBuilder = BaseConfig.ConfigBuilder()
                .setApplicationIdentifier(applicationName)
                .setConfigDirectory(findSetupDirectory(applicationName))
        BaseConfig.create(configBuilder)

        Log.DEBUG.debug("Current setup directory: ${BaseConfig.get().setupDirectory}")

        val props = Properties()
        props["spring.config.location"] = "file:${BaseConfig.get().setupDirectory}/"
        return props
    }

    private fun evaluateServerVersion(version: String?) : String {
        // if the implementation version is null (because there is no manifest file),
        // it is assumed that the application is running in a development environment
        if (version == null) {
            ServerContext.developerEnvironment = true
            val format = SimpleDateFormat("yyMMddHHmm")
            return format.format(Date())
        }
        return version
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
                if (directory.name == "${BaseConfig.get().applicationIdentifier}.setup") {
                    Log.DEBUG.info("Setup directory ${directory.name} found in ${directory.path}")
                    return "${System.getProperty("user.dir")}/${directory.name}"
                }
            }
            Log.DEBUG.error("Setup directory could not be found in current path ${System.getProperty("user.dir")}")
        }
        return aSetupDirectoryName
    }
}