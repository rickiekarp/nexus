package net.rickiekarp.foundation.parser.properties

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.utils.FileUtil
import java.io.*
import java.util.*

class PropertiesFileParser {
    companion object {

        /**
         * Loads all properties of a given .properties file.
         * @param clazz Current class (loader) to load the resources from
         * @param propertiesFile File path of the .properties file
         * @return Properties object of the given properties file
         */
        @JvmStatic
        fun readPropertiesFromFile(clazz: Class<*>, propertiesFile: String): Properties {
            val properties = Properties()
            val inputStream = clazz.classLoader.getResourceAsStream(propertiesFile)
            if (inputStream != null) {
                properties.load(inputStream)
            }
            return properties
        }

        @JvmStatic
        fun readPropertiesFileFromSetupDirectory(propertiesFile: String): Properties {
            val properties = Properties()

            val inputStream: InputStream
            try {
                inputStream = FileInputStream(BaseConfig.get().setupDirectory + File.separator + propertiesFile)
                properties.load(inputStream)
            } catch (e: FileNotFoundException) {
                println("Config [$propertiesFile]")
            }
            return properties
        }

        @JvmStatic
        fun writePropertiesFile(properties: Properties, filePath: String, fileName: String, comment: String) {
            FileUtil.checkCreateDirectories(File(filePath))
            val os: OutputStream
            try {
                os = FileOutputStream(filePath + File.separator + fileName + ".properties")
                properties.store(os, comment)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        @JvmStatic
        fun printProperties(propertiesMap: HashMap<String, Properties>) {
            for ((key1, value) in propertiesMap) {
                Log.DEBUG.debug("Properties loaded: " + value.size + " (" + key1 + ".properties)")
                if (Log.DEBUG.isDebugEnabled) {

                    val e = value.propertyNames()
                    var key: String
                    while (e.hasMoreElements()) {
                        key = e.nextElement() as String
                        Log.DEBUG.debug("Prop: " + key + " - " + value.getProperty(key))
                    }
                }
            }
        }
    }
}