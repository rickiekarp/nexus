package com.projekt.loginserver.rest.api

import com.projekt.backend.config.Configuration
import com.projekt.backend.config.ServerContext
import com.projekt.backend.dto.exception.ErrorDTO
import com.projekt.backend.model.Credentials
import com.projekt.backend.security.Secured
import com.projekt.loginserver.controller.TokenController
import com.projekt.loginserver.dto.AppObjectDTO
import com.projekt.loginserver.factory.AppObjectBuilder
import javax.servlet.ServletContext
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("account")
class AccountApi {

    @Path("login")
    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    fun login(@Context context: ServletContext): Response {
        try {
            val dto = AppObjectDTO(AppObjectBuilder())
            dto.serverVersion = ServerContext.get().serverVersion
            dto.setFeatureSettings(Configuration.properties)
            return Response.status(Response.Status.OK).entity(dto).build()
        } catch (e: Exception) {
            return Response.status(Response.Status.UNAUTHORIZED).build()
        }
    }

    @Path("create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun create(credentials: Credentials): Response {
        val user = ServerContext.get().loginDao.registerUser(credentials)
        if (user != null) {

            // Issue a token for the user
            val token = TokenController.issueToken(user)

            return Response.status(Response.Status.OK).entity(token).build()
        }
        return Response.status(Response.Status.NOT_FOUND).entity(ErrorDTO("User already registered!")).build()
    }
}
