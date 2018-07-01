//package com.rkarp.homeserver.rest.api
//
//import com.rkarp.foundation.config.ServerContext
//import com.rkarp.foundation.dao.UpdateDaoImpl
//import com.rkarp.foundation.parser.FileParser
//import org.w3c.dom.Document
//import java.io.IOException
//import java.io.StringWriter
//import java.util.jar.Manifest
//import javax.servlet.ServletContext
//import javax.servlet.http.HttpServletRequest
//import javax.ws.rs.GET
//import javax.ws.rs.Path
//import javax.ws.rs.Produces
//import javax.ws.rs.QueryParam
//import javax.ws.rs.core.Context
//import javax.ws.rs.core.MediaType
//import javax.ws.rs.core.Response
//import javax.xml.transform.OutputKeys
//import javax.xml.transform.TransformerFactory
//import javax.xml.transform.dom.DOMSource
//import javax.xml.transform.stream.StreamResult
//
//@Path("info")
//class InfoApi {
//
//    @GET
//    @Path("update")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Throws(IOException::class)
//    fun getUpdateInfo(
//            @QueryParam("identifier") appIdentifier: String,
//            @QueryParam("channel") updateChannel: Int): Response {
//
//        val application = UpdateDaoImpl().findByName(appIdentifier, updateChannel)
//
//        return Response.status(Response.Status.OK).entity(application).build()
//    }
//
//    @GET
//    @Path("version")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Throws(IOException::class)
//    fun getManifestAttributes(@Context context: ServletContext): Response {
//        val resourceAsStream = context.getResourceAsStream("/META-INF/MANIFEST.MF")
//        val mf = Manifest()
//        mf.read(resourceAsStream)
//        val manifestAttributes = mf.mainAttributes
//        return Response.status(Response.Status.OK).entity(manifestAttributes).build()
//    }
//
//    @GET
//    @Path("changelog")
//    @Produces(MediaType.APPLICATION_XML)
//    @Throws(IOException::class)
//    fun getChangelog(@QueryParam("identifier") appIdentifier: String): Response {
//        val xmlString = FileParser.readFileAndReturnString(ServerContext.serverContext.getHomeDirPath(AppServer::class.java) + "/Resources/" + appIdentifier + "/changelog.xml")
//        return Response.status(Response.Status.OK).entity(xmlString).build()
//    }
//
//    @GET
//    @Path("ip")
//    @Produces(MediaType.TEXT_PLAIN)
//    fun getIp(@Context req: HttpServletRequest): Response {
//        val remoteAddr = req.remoteAddr
//        val remotePort = req.remotePort
//        val remoteIp = remoteAddr + ":" + remotePort
//        return Response.status(200).entity(remoteIp).build()
//    }
//
//    private fun getDocumentString(doc: Document): String {
//        try {
//            val sw = StringWriter()
//            val tf = TransformerFactory.newInstance()
//            val transformer = tf.newTransformer()
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
//            transformer.setOutputProperty(OutputKeys.METHOD, "xml")
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
//            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
//
//            transformer.transform(DOMSource(doc), StreamResult(sw))
//            return sw.toString()
//        } catch (ex: Exception) {
//            throw RuntimeException("Error converting to String", ex)
//        }
//
//    }
//}
