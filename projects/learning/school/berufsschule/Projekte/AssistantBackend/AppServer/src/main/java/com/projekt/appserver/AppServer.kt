package com.projekt.appserver

import com.projekt.backend.config.ServletContextLoader
import org.glassfish.grizzly.servlet.WebappContext
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.servlet.ServletContainer
import java.io.IOException
import javax.ws.rs.core.UriBuilder

/**
 * Utility class which creates a [AppServerApplication] instance and runs the server from the command line
 */
class AppServer {
    private val BASE_URI = UriBuilder.fromUri("http://0.0.0.0").port(8080).path("/" + AppServer::class.java.simpleName).build()

    companion object {
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            AppServer().startServer()
        }
    }

    @Throws(IOException::class)
    private fun startServer() {
        // Create HttpServer and register dummy "not found" HttpHandler
        val httpServer = GrizzlyHttpServerFactory.createHttpServer(BASE_URI)

        // Initialize and register Jersey Servlet
        val context = WebappContext("webapp", "/")
        context.addListener(ServletContextLoader::class.java)
        val registration = context.addServlet("ServletContainer", ServletContainer::class.java)
        registration.setInitParameter("javax.ws.rs.Application", AppServerApplication::class.java.name)
        registration.addMapping("/" + AppServer::class.java.simpleName + "/api/*")
        context.deploy(httpServer)

        println("Application started. Hit enter to stop it...")
        System.`in`.read()
        httpServer.shutdownNow()
    }
}