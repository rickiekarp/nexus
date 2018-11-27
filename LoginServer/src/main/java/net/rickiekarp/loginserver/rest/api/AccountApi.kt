package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.loginserver.dao.UserDAO
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.loginserver.dto.AppObjectDTO
import net.rickiekarp.loginserver.dto.TokenDTO
import net.rickiekarp.loginserver.factory.AppObjectBuilder
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("account")
class AccountApi {

    @Autowired
    private val repo: UserDAO? = null

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @PostMapping(value = ["/authorize"])
    fun authenticateUser(@RequestBody credentialsDTO: Credentials): ResponseEntity<TokenDTO> {

        try {
            // Authenticate the user using the credentials provided
            val user = authenticate(credentialsDTO.username, credentialsDTO.password)

            // Issue a token for the user
            val token = issueToken(user)

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
        val authenticatedUser = repo!!.getUserByName(username!!)

        if (authenticatedUser != null) {
            if (password == authenticatedUser.password) {
                return authenticatedUser
            }
        }

        throw RuntimeException("unauthorized")
    }

    @RequestMapping(
            value = ["login"],
            method = arrayOf(RequestMethod.POST)
    )
    fun login(): ResponseEntity<AppObjectDTO> {
        return try {
            val dto = AppObjectDTO(AppObjectBuilder())
            dto.serverVersion = net.rickiekarp.foundation.config.ServerContext.serverVersion
            dto.setFeatureSettings(net.rickiekarp.foundation.config.Configuration.properties)
            ResponseEntity(dto, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.UNAUTHORIZED)
        }
    }

    @RequestMapping(
            value = ["create"],
            method = arrayOf(RequestMethod.POST)
    )
    fun create(@RequestBody credentials: Credentials): ResponseEntity<TokenDTO> {
        val user = repo!!.registerUser(credentials)
        val token: TokenDTO
        if (user != null) {

            // Issue a token for the user
            token = issueToken(user)

            return ResponseEntity(token, HttpStatus.OK)
        } else {
            token = TokenDTO("null")
        }

        return ResponseEntity(token, HttpStatus.OK)
        //return ResponseEntity(ErrorDTO("User already registered!"), HttpStatus.NOT_FOUND)
    }

    private fun issueToken(user: User): TokenDTO {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        val token = TokenDTO(Base64.getEncoder().encodeToString((user.userId.toString() + ":" + RandomStringUtils.randomAlphabetic(16)).toByteArray()))
        try {
            repo!!.updateUserToken(user, token.token)
            return token
        } catch (e: Exception) {
            println("Token could not be persisted into the database!")
            e.printStackTrace()
        }

        throw RuntimeException("error")
    }
}
