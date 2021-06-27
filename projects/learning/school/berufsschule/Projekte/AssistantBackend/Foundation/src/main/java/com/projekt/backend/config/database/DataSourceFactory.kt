package com.projekt.backend.config.database

import com.mysql.cj.jdbc.MysqlDataSource
import com.projekt.backend.config.Configuration
import java.sql.Connection
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

object DataSourceFactory {
    var loginDataSource: DataSource
    var appDataSource: DataSource

    init {
        println("Initializing object: $this")
        loginDataSource = createLoginDataSource(Configuration.dbProperties)
        appDataSource = createAppDataSource(Configuration.dbProperties)
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
        mysqlDS.setURL(props.getProperty("LOGINDB_URL"))
        mysqlDS.user = props.getProperty("LOGINDB_USER")
        mysqlDS.setPassword(props.getProperty("LOGINDB_PASSWORD"))
        return mysqlDS
    }

    private fun createAppDataSource(props: Properties): DataSource {
        val mysqlDS = MysqlDataSource()
        mysqlDS.setURL(props.getProperty("APPDB_URL"))
        mysqlDS.user = props.getProperty("APPDB_USER")
        mysqlDS.setPassword(props.getProperty("APPDB_PASSWORD"))
        return mysqlDS
    }
}