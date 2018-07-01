//package com.rkarp.homeserver.rest.api
//
//import com.rkarp.foundation.config.ServerContext
//import com.rkarp.homeserver.model.Plugin
//import com.rkarp.foundation.parser.XmlParser
//import org.w3c.dom.Element
//import java.util.ArrayList
//import javax.ws.rs.GET
//import javax.ws.rs.Path
//import javax.ws.rs.Produces
//import javax.ws.rs.QueryParam
//import javax.ws.rs.core.MediaType
//import javax.ws.rs.core.Response
//
//@Path("plugin")
//class PluginApi {
//
//    val pluginData: Response
//        @GET
//        @Path("data")
//        @Produces(MediaType.APPLICATION_JSON)
//        get() {
//            val moduleList = XmlParser.getNodeListFrom(ServerContext.serverContext.getHomeDirPath(AppServer::class.java) + "/Resources/botmanager/plugins.xml", "plugin") ?: return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
//
//            var labTest: Element
//            val pluginList = ArrayList<Plugin>(moduleList.length)
//            for (i in 0 until moduleList.length) {
//                labTest = moduleList.item(i) as Element
//                pluginList.add(Plugin(
//                        labTest.getAttribute("id"),
//                        labTest.getAttribute("version"),
//                        labTest.getAttribute("file"),
//                        labTest.getAttribute("type"),
//                        labTest.getAttribute("updateEnable")
//                ))
//            }
//            return Response.status(200).entity(pluginList).type(MediaType.APPLICATION_JSON).build()
//        }
//
//    @GET
//    @Path("name")
//    @Produces(MediaType.APPLICATION_JSON)
//    fun getPlugin(@QueryParam("plugin") pluginName: String): Response {
//        val moduleList = XmlParser.getNodeListFrom(ServerContext.serverContext.getHomeDirPath(AppServer::class.java) + "/Resources/botmanager/plugins.xml", "plugin") ?: return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
//
//        var labTest: Element
//        for (i in 0 until moduleList.length) {
//            labTest = moduleList.item(i) as Element
//            if (labTest.getAttribute("id") == pluginName) {
//                val plugin = Plugin(
//                        labTest.getAttribute("id"),
//                        labTest.getAttribute("version"),
//                        labTest.getAttribute("file"),
//                        labTest.getAttribute("type"),
//                        labTest.getAttribute("updateEnable")
//                )
//                return Response.status(200).entity(plugin).type(MediaType.APPLICATION_JSON).build()
//            }
//        }
//        return Response.status(404).build()
//    }
//
//}