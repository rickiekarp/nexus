package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.config.ServerContext
import net.rickiekarp.foundation.dto.exception.ResultDTO
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.loginserver.dao.UserDAO
import net.rickiekarp.loginserver.dto.AppObjectDTO
import net.rickiekarp.loginserver.dto.TokenDTO
import net.rickiekarp.loginserver.factory.AppObjectBuilder
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/account")
class AccountApi {

    @Autowired
    var repo: UserDAO? = null

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @PostMapping(value = ["/authorize"])
    fun authenticateUser(@RequestBody credentialsDTO: Credentials): ResponseEntity<TokenDTO> {

        return try {
            // Authenticate the user using the credentials provided
            val user = findUser(credentialsDTO.username, credentialsDTO.password)

            // Issue a token for the user
            issueToken(user)

            // Return the token on the response
            ResponseEntity(TokenDTO(user.token), HttpStatus.OK)

        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @Throws(RuntimeException::class)
    private fun findUser(username: String?, password: String?): User {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        val authenticatedUser = repo!!.getUserByName(username!!)

        if (authenticatedUser != null) {
            // validate credentials
            if (password == authenticatedUser.password) {
                return authenticatedUser
            }
        }

        throw RuntimeException("user not found")
    }

    @PostMapping(value = ["/login"])
    fun login(): ResponseEntity<AppObjectDTO> {
        return try {
            val dto = AppObjectDTO(AppObjectBuilder())
            dto.serverVersion = ServerContext.serverVersion
            dto.setFeatureSettings(BaseConfig.get().application())
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping(value = ["/create"])
    fun create(@RequestBody credentials: Credentials): ResponseEntity<Any> {
        val user = repo!!.registerUser(credentials)
        return if (user != null) {
            // Issue a token for the user
            val token = issueToken(user)
            ResponseEntity(token, HttpStatus.OK)
        } else {
            val result = ResultDTO("User could not be created!")
            ResponseEntity(result, HttpStatus.OK)
        }
    }

    private fun issueToken(user: User) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        val token = Base64.getEncoder().encodeToString((user.id.toString() + ":" + RandomStringUtils.randomAlphabetic(16)).toByteArray())
        try {
            repo!!.updateUserToken(user, token)
        } catch (e: Exception) {
            throw RuntimeException("Token could not be updated!")
        }
    }
}
