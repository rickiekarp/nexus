package com.rkarp.foundation.config;

import com.rkarp.foundation.core.types.OutputType;
import com.rkarp.foundation.logger.Log;
import com.rkarp.foundation.parser.properties.PropertiesFileParser;

import java.util.HashMap;
import java.util.Properties;

/**
 * Main config class
 * It is used in the support tool and test automation and holds all settings defined in the .properties files
 * of the respective application (applicationPropertyIdentifier).
 */
public class Config {
    private static Config config;
    private HashMap<String, Properties> applicationProperties;
    private String applicationPropertyIdentifier;
    private String setupDirectory;

    // ===========================================================
    // - - - - - - - - Game Configuration  - - - - - - - - - - - -
    // ===========================================================

    private Config(ConfigBuilder builder) {
        applicationPropertyIdentifier = builder.applicationIdentifier;

        applicationProperties = new HashMap<>(2);
    }

    public static void create(String applicationPropertyIdentifier) {
        ConfigBuilder builder = new ConfigBuilder()
                .setApplicationIdentifier(applicationPropertyIdentifier);
        create(builder);
    }

    /**
     * Main entry point when instantiating Config
     * When creating the config, it will first load all the settings from the .properties files and
     * set up all the needed services (like loggers)
     * @param builder
     */
    public static void create(ConfigBuilder builder) {
        config = builder.build();
        config.loadProperties(builder);
        config.configureServices();
    }

    /**
     * Load all properties for the current application.
     * By default it will first load all Foundation properties defined in foundation.properties
     * Afterwards it will try to load the .properties file of the given applicationIdentifier, so in case of the support tool
     * it will try to load the AdminServerWebapp.properties file
     * @param builder Current ConfigBuilder
     */
    private void loadProperties(ConfigBuilder builder) {
        addConfigProperties(Config.class, "foundation");
    }

    /**
     * Configures all needed services and prints the loaded properties
     */
    public void configureServices() {
        // Load properties
        Log.setupLogging();
        printProperties();
    }

    public static Config get() {
        return config;
    }

    public Properties foundation() {
        return applicationProperties.get("foundation");
    }

    public Properties application() {
        return applicationProperties.get(applicationPropertyIdentifier);
    }

    public <X> X getApplicationPropertyAs(String key, OutputType<X> outputType) {
        String propertyValue = application().getProperty(key);
        return outputType.convert(propertyValue);
    }

    public <X> X getConnectionPropertyAs(String key, OutputType<X> outputType) {
        String propertyValue = foundation().getProperty(key);
        return outputType.convert(propertyValue);
    }

    public String getApplicationIdentifier() {
        return applicationPropertyIdentifier;
    }

    private void addConfigProperties(final Class<?> clazz, final String key) {
        addConfigProperties(key, getPropertiesFromFile(clazz, key));
    }

    private void addConfigProperties(final String key, final Properties properties) {
        applicationProperties.put(key, properties);
    }

    private Properties getPropertiesFromFile(final Class<?> clazz, final String key) {
        return PropertiesFileParser.readPropertiesFromFile(clazz, key + ".properties");
    }

    private void printProperties() {
        PropertiesFileParser.printProperties(applicationProperties);
    }

    public void setSetupDirectory(String directory) {
        setupDirectory = directory;
    }

    public String getSetupDirectory() {
        return setupDirectory;
    }

    public static final class ConfigBuilder {
        private Class<?> clazz;

        private String applicationIdentifier;

        public ConfigBuilder setApplicationIdentifier(final String identifier) {
            applicationIdentifier = identifier;
            return this;
        }

        public Config build() {
            if (this.clazz == null) {
                // Using fallback location for application related property loading in case no class has been specified
                this.clazz = Config.class;
            }
            return new Config(this);
        }
    }
}
