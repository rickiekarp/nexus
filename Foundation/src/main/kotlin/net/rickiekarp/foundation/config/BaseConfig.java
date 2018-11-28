package net.rickiekarp.foundation.config;

import net.rickiekarp.foundation.core.types.OutputType;
import net.rickiekarp.foundation.logger.Log;
import net.rickiekarp.foundation.parser.properties.PropertiesFileParser;

import java.util.HashMap;
import java.util.Properties;

/**
 * Main baseConfig class
 */
public class BaseConfig {
    private static BaseConfig baseConfig;
    private HashMap<String, Properties> applicationProperties;
    private String applicationPropertyIdentifier;
    private String setupDirectory;

    private BaseConfig(ConfigBuilder builder) {
        applicationPropertyIdentifier = builder.applicationIdentifier;
        applicationProperties = new HashMap<>(2);
    }

    public static void create(String applicationPropertyIdentifier) {
        ConfigBuilder builder = new ConfigBuilder().setApplicationIdentifier(applicationPropertyIdentifier);
        create(builder);
    }

    /**
     * Main entry point when instantiating BaseConfig
     * When creating the baseConfig, it will first load all the settings from the .properties files and
     * set up all the needed services (like loggers)
     * @param builder
     */
    public static void create(ConfigBuilder builder) {
        baseConfig = builder.build();
        baseConfig.loadProperties(builder);
        baseConfig.configureServices();
    }

    /**
     * Load all properties for the current application.
     * By default it will first load all Foundation properties defined in foundation.properties
     * @param builder Current ConfigBuilder
     */
    private void loadProperties(ConfigBuilder builder) {
        addConfigProperties(BaseConfig.class, "foundation");
    }

    /**
     * Configures all needed services and prints the loaded properties
     */
    public void configureServices() {
        // Load properties
        Log.setupLogging();
        printProperties();
    }

    public static BaseConfig get() {
        return baseConfig;
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

        BaseConfig build() {
            if (this.clazz == null) {
                // Using fallback location for application related property loading in case no class has been specified
                this.clazz = BaseConfig.class;
            }
            return new BaseConfig(this);
        }
    }
}
