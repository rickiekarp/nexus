package com.projekt.backend.config

import java.io.IOException
import java.util.*

internal class ConfigLoader {

    fun read(clazz: Class<*>, propertiesFile: String): Properties {
        val properties = Properties()
        val inputStream = clazz.classLoader.getResourceAsStream(propertiesFile)
        if (inputStream != null) {
            try {
                properties.load(inputStream)
                println("Properties loaded: " + properties.size + " (" + propertiesFile + ")")
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return properties
    }
}