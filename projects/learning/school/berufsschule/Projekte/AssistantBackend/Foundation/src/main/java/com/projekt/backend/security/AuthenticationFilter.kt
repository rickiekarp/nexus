package com.projekt.backend.security

import com.projekt.backend.config.ServerContext
import org.glassfish.jersey.internal.util.Base64
import java.io.IOException
import java.util.*
import javax.annotation.Priority
import javax.ws.rs.NotAuthorizedException
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
class AuthenticationFilter : ContainerRequestFilter {

    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext) {

        // Get the HTTP Authorization header from the request
        val authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHENTICATION_SCHEME)) {
            throw NotAuthorizedException(Response.Status.UNAUTHORIZED.reasonPhrase)
        } else {

            //Get encoded username and password
            val encodedUserPassword = authorizationHeader.replaceFirst((AUTHENTICATION_SCHEME + " ").toRegex(), "")

            val username: Int
            try {
                // Validate the token
                //Decode username and password
                val usernameAndPassword = String(Base64.decode(encodedUserPassword.toByteArray()))

                //Split username and password tokens
                val tokenizer = StringTokenizer(usernameAndPassword, ":")
                username = Integer.parseInt(tokenizer.nextToken())
                val password = tokenizer.nextToken()

                //Verifying Username and password
                //System.out.println(username);
                //System.out.println(password);

                val authentificationResult = ServerContext.get().loginDao.getUserFromToken(encodedUserPassword) ?: throw NotAuthorizedException(Response.Status.UNAUTHORIZED.reasonPhrase)

                //Our system refuse login and password

                // Check if it was issued by the server and if it's not expired
                // Throw an Exception if the token is invalid
                if (username != authentificationResult.userId) {
                    throw NotAuthorizedException(Response.Status.UNAUTHORIZED.reasonPhrase)
                }


                //We configure your Security Context here
                val scheme = requestContext.uriInfo.requestUri.scheme
                requestContext.securityContext = CustomSecurityContext(authentificationResult, scheme)

            } catch (e: Exception) {
                throw NotAuthorizedException(Response.Status.UNAUTHORIZED.reasonPhrase)
            }

        }
    }

    companion object {
        private val AUTHENTICATION_SCHEME = "Basic"
    }
}