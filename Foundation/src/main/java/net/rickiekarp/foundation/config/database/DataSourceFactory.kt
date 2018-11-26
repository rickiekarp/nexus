package net.rickiekarp.foundation.config.database

import com.mysql.cj.jdbc.MysqlDataSource
import net.rickiekarp.foundation.config.Configuration
import org.springframework.context.ApplicationContext
import java.sql.Connection
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

object DataSourceFactory {
    lateinit var loginDataSource: DataSource
    lateinit var appDataSource: DataSource

    operator fun invoke(applicationName: String) {
        println("Initializing object: $this")
        net.rickiekarp.foundation.config.database.DataSourceFactory.loginDataSource = net.rickiekarp.foundation.config.database.DataSourceFactory.createLoginDataSource(net.rickiekarp.foundation.config.Configuration.dbProperties)
        net.rickiekarp.foundation.config.database.DataSourceFactory.appDataSource = net.rickiekarp.foundation.config.database.DataSourceFactory.createAppDataSource(applicationName, net.rickiekarp.foundation.config.Configuration.dbProperties)
    }

    @Throws(SQLException::class)
    fun getLoginConnection(): Connection {
        return net.rickiekarp.foundation.config.database.DataSourceFactory.loginDataSource.connection
    }

    @Throws(SQLException::class)
    fun getAppConnection(): Connection {
        return net.rickiekarp.foundation.config.database.DataSourceFactory.appDataSource.connection
    }

    private fun createLoginDataSource(props: Properties): DataSource {
        val mysqlDS = MysqlDataSource()
        mysqlDS.setServerName(props.getProperty("LOGINDB_URL"))
        mysqlDS.user = props.getProperty("LOGINDB_USER")
        mysqlDS.setPassword(props.getProperty("LOGINDB_PASSWORD"))
        return mysqlDS
    }

    private fun createAppDataSource(applicationName: String, props: Properties): DataSource {
        val mysqlDS = MysqlDataSource()
        val databaseConnectionPrefix = net.rickiekarp.foundation.config.database.DataSourceFactory.getDatabaseConnectionPrefixStringFromContext(applicationName)
        mysqlDS.setServerName(props.getProperty(databaseConnectionPrefix + "_URL"))
        mysqlDS.user = props.getProperty(databaseConnectionPrefix + "_USER")
        mysqlDS.setPassword(props.getProperty(databaseConnectionPrefix + "_PASSWORD"))
        return mysqlDS
    }

    private fun getDatabaseConnectionPrefixStringFromContext(applicationName: String) : String {
        return when (applicationName) {
            "LoginServer" -> { "LOGINDB" }
            "AdminTool" -> { "ADMINDB"}
            "HomeServer" -> { "APPDB" }
            "AssistantServer" -> { "ASSISTANTDB" }
            else -> {
                throw RuntimeException("Invalid server identifier: $applicationName")
            }
        }
    }

    fun getDataSourceFromContext(servletContext: ApplicationContext) : DataSource {
        return when (servletContext.displayName) {
            "LoginServer" -> {
                net.rickiekarp.foundation.config.database.DataSourceFactory.loginDataSource
            }
            else -> {
                net.rickiekarp.foundation.config.database.DataSourceFactory.appDataSource
            }
        }
    }
}