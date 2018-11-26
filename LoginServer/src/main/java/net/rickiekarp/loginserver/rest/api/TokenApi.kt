package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.loginserver.controller.TokenController
import net.rickiekarp.loginserver.dto.TokenDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class TokenApi {

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @RequestMapping(value = ["/token"], method = [RequestMethod.POST])
    fun authenticateUser(credentialsDTO: Credentials): ResponseEntity<TokenDTO> {
        try {
            // Authenticate the user using the credentials provided
            val user = authenticate(credentialsDTO.username, credentialsDTO.password)

            // Issue a token for the user
            val token = TokenController.issueToken(user)

            // Return the token on the response
            return ResponseEntity(token, HttpStatus.OK)

        } catch (e: RuntimeException) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @RequestMapping(
            value = ["validateProperties"],
            method = [RequestMethod.GET]
    )
    fun validateProperties(): ResponseEntity<String> {
        return ResponseEntity("asdasd", HttpStatus.OK)
    }

    @Throws(RuntimeException::class)
    private fun authenticate(username: String?, password: String?): User {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        val authenticatedUser = net.rickiekarp.foundation.config.ServerContext.loginDao.getUserByName(username!!)

        if (authenticatedUser != null) {
            if (password == authenticatedUser.password) {
                return authenticatedUser
            }
        }
        throw RuntimeException("unauthorized")
    }
}