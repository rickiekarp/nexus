package net.rickiekarp.foundation.logger

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.core.types.OutputType
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.appender.ConsoleAppender
import org.apache.logging.log4j.core.appender.FileAppender
import org.apache.logging.log4j.core.config.ConfigurationFactory
import org.apache.logging.log4j.core.config.Order
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration
import org.apache.logging.log4j.core.config.plugins.Plugin
import java.io.File

@Plugin(name = "CustomLoggerConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(100)
class CustomLoggerConfigurationFactory {

    fun createConfiguration(name: String, logLevel: String, builder: ConfigurationBuilder<BuiltConfiguration>): ConfigurationBuilder<BuiltConfiguration> {
        builder.setConfigurationName(name)
        builder.setStatusLevel(Level.ERROR)

        val rootLoggerComponentBuilder = builder.newRootLogger(logLevel)
        val loggerComponentBuilder = builder.newLogger("org.apache.logging.log4j", logLevel).addAttribute("additivity", false)
        val showInConsole = BaseConfig.get().getFoundationPropertyAs("Log.debug.showInConsole", OutputType.BOOLEAN)
        when (name) {
            "log.debug" -> {
                if (showInConsole) {
                    setupConsoleAppender(builder, rootLoggerComponentBuilder)
                    val stdout = "Stdout"
                    loggerComponentBuilder.add(builder.newAppenderRef(stdout))
                    builder.add(getFilterComponentBuilder(builder, logLevel))
                }
                if (BaseConfig.get().getFoundationPropertyAs("Log.debug.writeLogFile", OutputType.BOOLEAN)) {
                    val fileRef = "fileRef"
                    setupFileAppender(name, builder, rootLoggerComponentBuilder)
                    loggerComponentBuilder.add(builder.newAppenderRef(fileRef))
                    builder.add(rootLoggerComponentBuilder.add(builder.newAppenderRef(fileRef)))
                    if (!showInConsole) {
                        builder.add(getFilterComponentBuilder(builder, logLevel))
                    }
                }
            }
        }
        return builder
    }

    private fun setupConsoleAppender(builder: ConfigurationBuilder<BuiltConfiguration>, rootBuilder: RootLoggerComponentBuilder) {
        val appenderBuilder = builder.newAppender("Stdout", ConsoleAppender.PLUGIN_NAME).addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)

        appenderBuilder.add(builder.newLayout("PatternLayout").addAttribute("pattern", BaseConfig.get().foundation().getProperty("LogPattern")))
        appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY, Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"))

        builder.add(appenderBuilder)
        rootBuilder.add(builder.newAppenderRef("Stdout"))
    }

    private fun setupFileAppender(name: String, builder: ConfigurationBuilder<BuiltConfiguration>, rootBuilder: RootLoggerComponentBuilder) {
        val appenderBuilder = builder.newAppender("fileRef", FileAppender.PLUGIN_NAME)

        appenderBuilder.addAttribute("fileName",
                System.getProperty("user.dir") + File.separator + BaseConfig.get().foundation().getProperty("Log.path") + File.separator + name + ".log"
        )
        appenderBuilder.add(builder.newLayout("PatternLayout").addAttribute("pattern", BaseConfig.get().foundation().getProperty("LogPattern")))
        appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY, Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"))

        builder.add(appenderBuilder)
        rootBuilder.add(builder.newAppenderRef("fileRef"))
    }

    private fun getFilterComponentBuilder(builder: ConfigurationBuilder<BuiltConfiguration>, logLevel: String): FilterComponentBuilder {
        val filterComponentBuilder = builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL)
        filterComponentBuilder.addAttribute("level", logLevel)
        return filterComponentBuilder
    }
}
