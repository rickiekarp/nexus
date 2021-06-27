package com.projekt.appserver.rest.api

import com.projekt.backend.parser.ManifestParser
import java.io.IOException
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("info")
class InfoApi {

    @GET
    @Path("version")
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(IOException::class)
    fun getManifestAttributes(@Context context: ServletContext): Response {
        val manifestAttributes = ManifestParser.getManifestFromContext(context).mainAttributes
        return Response.status(Response.Status.OK).entity(manifestAttributes).build()
    }

    @GET
    @Path("ip")
    @Produces(MediaType.TEXT_PLAIN)
    fun getIp(@Context req: HttpServletRequest): Response {
        val remoteAddr = req.remoteAddr
        val remotePort = req.remotePort
        val remoteIp = remoteAddr + ":" + remotePort
        return Response.status(200).entity(remoteIp).build()
    }
}