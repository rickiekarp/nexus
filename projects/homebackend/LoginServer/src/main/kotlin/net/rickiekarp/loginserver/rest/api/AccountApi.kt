package net.rickiekarp.loginserver.rest.api

import net.rickiekarp.foundation.config.BaseConfig
import net.rickiekarp.foundation.config.ServerContext
import net.rickiekarp.foundation.data.dto.ResultDTO
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.loginserver.dao.UserDAO
import net.rickiekarp.loginserver.dto.AppObjectDTO
import net.rickiekarp.loginserver.dto.TokenDTO
import net.rickiekarp.loginserver.utils.HashingUtil
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

    @Autowired
    private val hashingUtil: HashingUtil? = null

    /**
     * Checks whether the user is allowed to execute the requested plugin
     * @param pluginIdentifierJson Plugin to check
     * @return True if user is allowed, false otherwise
     */
    @PostMapping(value = ["/authorize"])
    fun authenticateUser(@RequestBody credentialsDTO: Credentials): ResponseEntity<TokenDTO> {

        return try {
            // Authenticate the user using the credentials provided
            val user = findUser(credentialsDTO)

            // Issue a token for the user
            val token = issueToken(user)

            // Return the token on the response
            ResponseEntity(TokenDTO(token), HttpStatus.OK)

        } catch (e: RuntimeException) {
            Log.DEBUG.error("Exception", e)
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @Throws(RuntimeException::class)
    private fun findUser(credentialsDTO: Credentials): User {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        val retrievedUser = repo!!.getUserByName(credentialsDTO.username!!)
        if (retrievedUser != null) {
            if (retrievedUser.isAccountEnabled) {
                // validate credentials
                if (hashingUtil!!.generateStrongPasswordHash(credentialsDTO.password!!) == retrievedUser.password) {
                    return retrievedUser
                } else {
                    throw RuntimeException("password incorrect")
                }
            } else {
                throw RuntimeException("account disabled")
            }
        } else {
            throw RuntimeException("user not found")
        }
    }

    @PostMapping(value = ["/login"])
    fun login(): ResponseEntity<AppObjectDTO> {
        val dto = AppObjectDTO()
        return if (repo!!.doLogin(BaseConfig.get().getUserId())) {
            dto.serverVersion = ServerContext.serverVersion
            dto.setFeatureSettings(BaseConfig.get().application())
            ResponseEntity(dto, HttpStatus.OK)
        } else {
            ResponseEntity(dto, HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping(value = ["/create"])
    fun create(@RequestBody credentials: Credentials): ResponseEntity<Any> {
        val user = repo!!.registerUser(credentials)
        return if (user != null) {
            // Issue a token for the user
            val token = issueToken(user)
            ResponseEntity(TokenDTO(token), HttpStatus.OK)
        } else {
            val result = ResultDTO("User could not be created!")
            ResponseEntity(result, HttpStatus.OK)
        }
    }

    private fun issueToken(user: User): String {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        val token = RandomStringUtils.randomAlphabetic(24)
        try {
            repo!!.updateUserToken(user, token)
        } catch (e: Exception) {
            throw RuntimeException("Token could not be updated!")
        }
        return Base64.getEncoder().encodeToString((user.id.toString() + ":" + token).toByteArray())
    }

    @PostMapping(value = ["/recover"])
    fun recoverAccount(@RequestBody credentials: Credentials): ResponseEntity<Any> {
        Log.DEBUG.error(credentials.username)
        return ResponseEntity(ResultDTO("not_implemented_yet"), HttpStatus.OK)
    }
}
