package net.rickiekarp.foundation.security

import net.rickiekarp.foundation.config.redis.TokenRepository
import net.rickiekarp.foundation.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.io.IOException
import java.io.PrintWriter

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import java.util.*

class BasicAuthEncryPoint : BasicAuthenticationEntryPoint() {

    companion object {
        private val AUTHENTICATION_SCHEME = "Basic"
    }

    @Autowired
    private val tokenService: TokenRepository? = null

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest?,
                          response: HttpServletResponse,
                          authException: AuthenticationException?) {
        println("commence ${request!!.requestURL}")


        /* Get token from header */
        var authorizationHeader: String? = request!!.getHeader(HttpHeaders.AUTHORIZATION)
        /* If token not found get it from request parameter */
        if (authorizationHeader != null) {
            //Get encoded username and password
            val encodedUserPassword = authorizationHeader!!.replaceFirst((AUTHENTICATION_SCHEME + " ").toRegex(), "")

            val username: Int

            // Validate the token
            //Decode username and password
            val usernameAndPassword = String(Base64.getDecoder().decode(encodedUserPassword.toByteArray()))

            //Split username and password tokens
            val tokenizer = StringTokenizer(usernameAndPassword, ":")
            username = Integer.parseInt(tokenizer.nextToken())
            val password = tokenizer.nextToken()

            println("username" + username)
            println("password" +password)
            println("usernameAndPassword: " +usernameAndPassword)

            val user = retrieveRedis(username)

            if (user.isPresent && user.get().token.equals(encodedUserPassword)) {
                println("authentication success")
                val authentication = UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities())
                SecurityContextHolder.getContext().authentication = authentication
            }
        } else {
            println("no header")
            //Authentication failed, send error response.
            response.status = HttpServletResponse.SC_PAYMENT_REQUIRED
            response.addHeader("WWW-Authenticate", "Basic realm=$realmName")

            val writer = response.writer
            writer.println("HTTP Status 401 : " + authException!!.message)
        }


    }

    private fun retrieveRedis(userid: Int): Optional<User> {
        println("token: $userid")
        val retrievedStudent = tokenService!!.findById(userid.toString())
        println("token: $retrievedStudent")
        return retrievedStudent
    }

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        realmName = "MY_TEST_REALM"
        super.afterPropertiesSet()
    }
}