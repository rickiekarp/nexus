package com.projekt.backend.config

import com.projekt.backend.dao.UserDAO
import com.projekt.backend.dao.UserDaoImpl
import com.projekt.backend.migration.DatabaseMigrationController

class ServerContext private constructor() {
    var serverVersion: String? = null
        internal set
    val databaseController: DatabaseMigrationController
    val loginDao: UserDAO

    companion object {
        lateinit var serverContext: ServerContext
        lateinit var serverConfig: Configuration

        fun create() {
            serverContext = ServerContext()
            serverConfig = Configuration()
        }

        fun get(): ServerContext {
            return serverContext
        }
    }

    init {
        serverContext = this
        databaseController = DatabaseMigrationController()
        loginDao = UserDaoImpl()
    }

    fun getServerConfig(): Configuration? {
        return serverConfig
    }
}