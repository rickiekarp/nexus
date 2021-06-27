package com.projekt.backend.config

import com.projekt.backend.parser.ManifestParser
import com.projekt.backend.utils.DateUtil
import javax.servlet.ServletContext
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

class ServletContextLoader : ServletContextListener {
    override fun contextInitialized(arg0: ServletContextEvent?) {
        println("initContext")
        //use the ServletContextEvent argument to access the
        //parameter from the context-param
        //String my_param = arg0.getServletContext().getInitParameter("your_param");

        ServerContext.create()

        parseServerManifest(arg0!!.servletContext)
    }

    /**
     * Parses the WEB-INF/MANIFEST.MF properties
     * @param context Current servlet context
     */
    private fun parseServerManifest(context: ServletContext) {
        val manifest = ManifestParser.getManifestFromContext(context)
        val version = ManifestParser.readManifestProperty(manifest, "Build-Time")
        if (version == null) {
            println("Assuming dev environment!")
            ServerContext.get().serverVersion = DateUtil.getDate("yyMMddHHmm")
        } else {
            ServerContext.get().serverVersion = version
        }
        println("Server version: " + ServerContext.get().serverVersion)
    }

    override fun contextDestroyed(arg0: ServletContextEvent?) {
        println("ServletContextLoader: contextDestroyed()")
    }
}