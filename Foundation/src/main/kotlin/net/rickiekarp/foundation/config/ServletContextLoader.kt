//package net.rickiekarp.foundation.config
//
//import net.rickiekarp.foundation.config.database.DataSourceFactory
//import net.rickiekarp.foundation.config.database.DatabaseMigrationController
//import net.rickiekarp.foundation.parser.ManifestParser
//import net.rickiekarp.foundation.utils.DateUtil
//import javax.servlet.ServletContext
//import javax.servlet.ServletContextEvent
//import javax.servlet.ServletContextListener
//
//class ServletContextLoader : ServletContextListener {
//
//    override fun contextInitialized(arg0: ServletContextEvent) {
//        println("initContext: " + arg0.servletContext.servletContextName)
//        //use the ServletContextEvent argument to access the
//        //parameter from the context-param
//        //String my_param = arg0.getServletContext().getInitParameter("your_param");
//
//        ServerContext.create()
//
//        // Set up server context and load settings
//        ServerContext.serverConfig.loadDatabaseProperties(this.javaClass)
//
//        DataSourceFactory(arg0.servletContext)
//
//        val databaseMigrationController = DatabaseMigrationController()
//        databaseMigrationController.migrateDatabase(arg0.servletContext)
//
//        parseServerManifest(arg0.servletContext)
//    }
//
//    /**
//     * Parses the WEB-INF/MANIFEST.MF properties
//     * @param context Current servlet context
//     */
//    private fun parseServerManifest(context: ServletContext) {
//        val manifest = ManifestParser.getManifestFromContext(context)
//        val version = ManifestParser.readManifestProperty(manifest, "Build-Time")
//        val serverContext = ServerContext.serverContext
//        if (version == null) {
//            println("Assuming dev environment!")
//            serverContext.serverVersion = DateUtil.getDate("yyMMddHHmm")
//        } else {
//            serverContext.serverVersion = version
//        }
//        println("Server version: " + ServerContext.get().serverVersion)
//    }
//
//    override fun contextDestroyed(arg0: ServletContextEvent?) {
//        println("ServletContextLoader: contextDestroyed()")
//    }
//}