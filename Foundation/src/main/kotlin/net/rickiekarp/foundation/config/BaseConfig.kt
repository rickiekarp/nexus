package net.rickiekarp.foundation.config

import net.rickiekarp.foundation.core.types.OutputType
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.parser.properties.PropertiesFileParser

import java.util.HashMap
import java.util.Properties

/**
 * Main baseConfig class
 */
class BaseConfig private constructor(builder: ConfigBuilder) {
    private val applicationProperties: HashMap<String, Properties>
    val applicationIdentifier: String
    var setupDirectory: String? = null

    init {
        applicationIdentifier = builder.applicationIdentifier
        setupDirectory = builder.setupDirectory
        applicationProperties = HashMap(2)
    }

    /**
     * Load all properties for the current application.
     * By default it will first load all Foundation properties defined in foundation.properties
     * @param builder Current ConfigBuilder
     */
    private fun loadProperties(builder: ConfigBuilder) {
        addConfigProperties(BaseConfig::class.java, "foundation")
        addConfigProperties(null, builder.applicationIdentifier)
    }

    /**
     * Configures all needed services and prints the loaded properties
     */
    fun configureServices() {
        // Load properties
        Log.setupLogging()
        printProperties()
    }

    fun foundation(): Properties {
        return applicationProperties["foundation"]!!
    }

    fun application(): Properties {
        return applicationProperties[applicationIdentifier]!!
    }

    fun <X> getApplicationPropertyAs(key: String, outputType: OutputType<X>): X {
        val propertyValue = application().getProperty(key)
        return outputType.convert(propertyValue)
    }

    fun <X> getConnectionPropertyAs(key: String, outputType: OutputType<X>): X {
        val propertyValue = foundation().getProperty(key)
        return outputType.convert(propertyValue)
    }

    private fun addConfigProperties(clazz: Class<*>?, key: String) {
        if (clazz != null) {
            addConfigProperties(key, getPropertiesFromFile(clazz, key))
        } else {
            addConfigProperties(key, getPropertiesFromSetupFile(key))
        }
    }

    private fun addConfigProperties(key: String, properties: Properties) {
        applicationProperties[key] = properties
    }

    private fun getPropertiesFromFile(clazz: Class<*>, key: String): Properties {
        return PropertiesFileParser.readPropertiesFromFile(clazz, "$key.properties")
    }

    private fun getPropertiesFromSetupFile(keyFile: String): Properties {
        return PropertiesFileParser.readPropertiesFileFromSetupDirectory("$keyFile.properties")
    }

    private fun printProperties() {
        PropertiesFileParser.printProperties(applicationProperties)
    }

    class ConfigBuilder {
        private var clazz: Class<*>? = null

        lateinit var applicationIdentifier: String
        lateinit var setupDirectory: String

        fun setApplicationIdentifier(identifier: String): ConfigBuilder {
            applicationIdentifier = identifier
            return this
        }

        fun setConfigDirectory(directory: String): ConfigBuilder {
            setupDirectory = directory
            return this
        }

        internal fun build(): BaseConfig {
            if (this.clazz == null) {
                // Using fallback location for application related property loading in case no class has been specified
                this.clazz = BaseConfig::class.java
            }
            return BaseConfig(this)
        }
    }

    companion object {
        private lateinit var baseConfig: BaseConfig

        fun create(applicationPropertyIdentifier: String) {
            val builder = ConfigBuilder().setApplicationIdentifier(applicationPropertyIdentifier)
            create(builder)
        }

        /**
         * Main entry point when instantiating BaseConfig
         * When creating the baseConfig, it will first load all the settings from the .properties files and
         * set up all the needed services (like loggers)
         * @param builder
         */
        fun create(builder: ConfigBuilder) {
            baseConfig = builder.build()
            baseConfig.loadProperties(builder)
            baseConfig.configureServices()
        }

        fun get(): BaseConfig {
            return baseConfig
        }
    }
}
