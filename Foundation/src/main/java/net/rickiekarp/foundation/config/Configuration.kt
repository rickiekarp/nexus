package net.rickiekarp.foundation.config

import java.util.*

class Configuration {
    private val loader: net.rickiekarp.foundation.config.ConfigLoader = net.rickiekarp.foundation.config.ConfigLoader()
    private val databasePropertiesFile = "config/db.properties"

    companion object {
        var dbProperties = Properties()
        var properties = Properties()
    }

    fun loadDatabaseProperties(clazz: Class<*>) {
        net.rickiekarp.foundation.config.Configuration.Companion.dbProperties = loader.read(clazz, databasePropertiesFile)
    }

    fun load(clazz: Class<*>, propertiesFile: String) {
        net.rickiekarp.foundation.config.Configuration.Companion.properties = loader.read(clazz, propertiesFile)
    }

    fun printProperties() {
        println(net.rickiekarp.foundation.config.Configuration.Companion.properties.size)
        val p = net.rickiekarp.foundation.config.Configuration.Companion.properties
        val keys = p.keys()
        while (keys.hasMoreElements()) {
            val key = keys.nextElement() as String
            val value = p[key] as String
            println(key + ": " + value)
        }
    }
}