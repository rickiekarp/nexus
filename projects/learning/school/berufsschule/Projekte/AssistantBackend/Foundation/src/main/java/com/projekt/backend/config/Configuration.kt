package com.projekt.backend.config

import java.util.*

class Configuration {
    private val loader: ConfigLoader = ConfigLoader()
    private val databasePropertiesFile = "config/db.properties"

    companion object {
        var dbProperties = Properties()
        var properties = Properties()
    }

    fun loadDatabaseProperties(clazz: Class<*>) {
        dbProperties = loader.read(clazz, databasePropertiesFile)
    }

    fun load(clazz: Class<*>, propertiesFile: String) {
        properties = loader.read(clazz, propertiesFile)
    }

    fun printProperties() {
        println(Configuration.properties.size)
        val p = Configuration.properties
        val keys = p.keys()
        while (keys.hasMoreElements()) {
            val key = keys.nextElement() as String
            val value = p[key] as String
            println(key + ": " + value)
        }
    }
}