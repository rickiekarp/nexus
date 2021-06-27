package com.projekt.loginserver.rest.api

import com.projekt.backend.config.Configuration
import com.projekt.backend.dto.exception.ResultDTO
import com.projekt.backend.security.Secured
import com.projekt.loginserver.dto.KeyValuePairDTO
import java.util.ArrayList
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("admin")
class AdminApi {

    @Path("getFeatureFlags")
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getFeatureFlags(): Response {
        val keyValueList = ArrayList<KeyValuePairDTO>()

        for (key in Configuration.properties.stringPropertyNames()) {
            val value = Configuration.properties.getProperty(key)
            val keyValuePair = KeyValuePairDTO(key, value)
            keyValueList.add(keyValuePair)
            println(key + " => " + value)
        }

        return Response.status(Response.Status.OK).entity(keyValueList).build()
    }

    @Path("updateFeatureFlag")
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun updateFeatureFlag(keyValue: KeyValuePairDTO): Response {
        if (Configuration.properties.containsKey(keyValue.key)) {
            Configuration.properties.setProperty(keyValue.key, keyValue.value)
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(ResultDTO("Key not found!")).build()
        }
        return Response.status(Response.Status.OK).entity(ResultDTO("success")).build()
    }
}
