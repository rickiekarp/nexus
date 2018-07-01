//package com.rkarp.foundation
//
//import com.rkarp.foundation.config.ServerContext
//import com.rkarp.foundation.config.database.DataSourceFactory
//import com.rkarp.foundation.config.database.DatabaseMigrationController
//import org.glassfish.grizzly.servlet.WebappContext
//import org.junit.Test
//
//class DatabaseMigrationTest {
//    var context: WebappContext
//
//    init {
//        // Set up server context and load settings
//        ServerContext.create()
//        ServerContext.serverConfig.loadDatabaseProperties(this.javaClass)
//
//        context = WebappContext("LoginServer", "/")
//        DataSourceFactory(context)
//    }
//
//    @Test
//    fun testMigrateLoginDatabase() {
//        val controller = DatabaseMigrationController()
//        controller.migrateDatabase(context)
//    }
//}