package net.rickiekarp.foundation.config.redis

import net.rickiekarp.foundation.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomAuthenticationProvider : AuthenticationProvider {

    @Autowired
    private val tokenService: TokenRepository? = null

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val userid = authentication.name.toInt()
        val password = authentication.credentials.toString()

        return if (shouldAuthenticateAgainstThirdPartySystem(userid, password)) {
            // use the credentials
            // and authenticate against the third-party system
            UsernamePasswordAuthenticationToken(userid, password, ArrayList<GrantedAuthority>())
        } else {
            println("UserId[$userid] could not be authenticated!")
            null
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    private fun shouldAuthenticateAgainstThirdPartySystem(userid: Int, password: String): Boolean {
        val user = retrieveUserFromRedis(userid)
        if (user.isPresent) {
            return user.get().password.equals(password)
        }
        return false
    }

    private fun retrieveUserFromRedis(userid: Int): Optional<User> {
        return tokenService!!.findById(userid.toString())
    }
}