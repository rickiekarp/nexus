package com.projekt.appserver

import com.projekt.backend.config.ServerContext
import com.projekt.backend.config.database.DataSourceFactory
import com.projekt.backend.mapper.NotAuthorizedMapper
import com.projekt.backend.mapper.NotFoundExceptionMapper
import com.projekt.backend.mapper.UnexpectedExceptionMapper
import com.projekt.backend.security.AuthenticationFilter
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.logging.LoggingFeature
import org.glassfish.jersey.server.ResourceConfig
import java.util.logging.Level
import java.util.logging.Logger

class AppServerApplication : ResourceConfig() {
    init {
        println("Starting " + AppServer::class.java.simpleName)

        // Set up server context and load settings
        ServerContext.serverConfig.loadDatabaseProperties(this.javaClass)
        ServerContext.get().databaseController.migrateDatabase(DataSourceFactory.appDataSource)

        DataSourceFactory

        packages("com.projekt.appserver")
        register(LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.SEVERE, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000))
        register(AuthenticationFilter::class.java)
        register(JacksonFeature::class.java)
        register(NotAuthorizedMapper::class.java)
        register(NotFoundExceptionMapper::class.java)
        register(UnexpectedExceptionMapper::class.java)
    }
}