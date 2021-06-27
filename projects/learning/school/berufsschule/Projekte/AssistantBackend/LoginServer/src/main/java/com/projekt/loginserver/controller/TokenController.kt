package com.projekt.loginserver.controller

import com.projekt.backend.config.ServerContext
import com.projekt.backend.model.User
import com.projekt.loginserver.dto.TokenDTO
import org.apache.commons.lang3.RandomStringUtils
import org.glassfish.jersey.internal.util.Base64
import javax.ws.rs.InternalServerErrorException

object TokenController {

    init {
        println("Initializing object: $this")
    }

    @Synchronized
    fun issueToken(user: User): TokenDTO {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        val token = TokenDTO(Base64.encodeAsString(user.userId.toString() + ":" + RandomStringUtils.randomAlphabetic(16)))
        try {
            ServerContext.get().loginDao.updateUserToken(user, token.token)
            return token
        } catch (e: Exception) {
            println("Token could not be persisted into the database!")
            e.printStackTrace()
        }

        throw InternalServerErrorException("error")
    }
}