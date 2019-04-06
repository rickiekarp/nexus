package net.rickiekarp.foundation.config

import java.util.*

class Configuration {
    private val loader: ConfigLoader = ConfigLoader()

    companion object {
        var properties = Properties()
    }

    fun load(clazz: Class<*>, propertiesFile: String) {
        Configuration.properties = loader.read(clazz, propertiesFile)
    }

    fun printProperties() {
        println(Configuration.Companion.properties.size)
        val p = Configuration.Companion.properties
        val keys = p.keys()
        while (keys.hasMoreElements()) {
            val key = keys.nextElement() as String
            val value = p[key] as String
            println(key + ": " + value)
        }
    }
}