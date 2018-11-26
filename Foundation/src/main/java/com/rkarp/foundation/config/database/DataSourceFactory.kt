package com.rkarp.foundation.config.database

import com.mysql.cj.jdbc.MysqlDataSource
import com.rkarp.foundation.config.Configuration
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
        loginDataSource = createLoginDataSource(Configuration.dbProperties)
        appDataSource = createAppDataSource(applicationName, Configuration.dbProperties)
    }

    @Throws(SQLException::class)
    fun getLoginConnection(): Connection {
        return loginDataSource.connection
    }

    @Throws(SQLException::class)
    fun getAppConnection(): Connection {
        return appDataSource.connection
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
        val databaseConnectionPrefix = getDatabaseConnectionPrefixStringFromContext(applicationName)
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
            "LoginServer" -> { loginDataSource }
            else -> {
                appDataSource
            }
        }
    }
}