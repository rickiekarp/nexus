package com.projekt.loginserver.rest.api

import com.projekt.backend.config.ServerContext
import com.projekt.backend.model.Credentials
import com.projekt.backend.model.User
import com.projekt.loginserver.controller.TokenController
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("auth")
class TokenApi {

    @Path("token")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun authenticateUser(credentialsDTO: Credentials): Response {
        try {
            // Authenticate the user using the credentials provided
            val user = authenticate(credentialsDTO.username, credentialsDTO.password)

            // Issue a token for the user
            val token = TokenController.issueToken(user)

            // Return the token on the response
            return Response.status(200).entity(token).build()
        } catch (e: NotAuthorizedException) {
            return Response.status(Response.Status.UNAUTHORIZED).build()
        }

    }

    @Throws(NotAuthorizedException::class)
    private fun authenticate(username: String?, password: String?): User {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        val authenticatedUser = ServerContext.get().loginDao.getUserByName(username!!)

        if (authenticatedUser != null) {
            if (password == authenticatedUser.password) {
                return authenticatedUser
            }
        }
        throw NotAuthorizedException("unauthorized")
    }
}