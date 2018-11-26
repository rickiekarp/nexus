package net.rickiekarp.loginserver.controller

import net.rickiekarp.foundation.model.User
import net.rickiekarp.loginserver.dto.TokenDTO
import org.apache.commons.lang3.RandomStringUtils
import java.util.*

object TokenController {

    init {
        println("Initializing object: $this")
    }

    @Synchronized
    fun issueToken(user: User): TokenDTO {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        val token = TokenDTO(Base64.getEncoder().encodeToString((user.userId.toString() + ":" + RandomStringUtils.randomAlphabetic(160)).toByteArray()))
        try {
            net.rickiekarp.foundation.config.ServerContext.loginDao.updateUserToken(user, token.token)
            return token
        } catch (e: Exception) {
            println("Token could not be persisted into the database!")
            e.printStackTrace()
        }

        throw RuntimeException("error")
    }
}