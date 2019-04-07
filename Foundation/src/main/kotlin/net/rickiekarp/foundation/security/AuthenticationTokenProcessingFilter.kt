//package net.rickiekarp.foundation.security
//
//import java.io.IOException
//import javax.servlet.http.HttpServletRequest
//
//import net.rickiekarp.foundation.config.redis.TokenRepository
//import net.rickiekarp.foundation.model.User
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpHeaders
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.stereotype.Component
//import org.springframework.web.filter.GenericFilterBean
//import java.util.*
//import javax.servlet.*
//
//@Component
//class AuthenticationTokenProcessingFilter : GenericFilterBean() {
//
//    companion object {
//        private val AUTHENTICATION_SCHEME = "Basic"
//    }
//
//    @Autowired
//    private val tokenService: TokenRepository? = null
//
//    @Throws(IOException::class, ServletException::class)
//    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
//        val httpRequest = this.getAsHttpRequest(request)
//        println("filtering... ${httpRequest.requestURL}")
//
//        /* Get token from header */
//        var authorizationHeader: String? = httpRequest.getHeader(HttpHeaders.AUTHORIZATION)
//        /* If token not found get it from request parameter */
//        if (authorizationHeader != null) {
//            //Get encoded username and password
//            val encodedUserPassword = authorizationHeader!!.replaceFirst((AUTHENTICATION_SCHEME + " ").toRegex(), "")
//
//            val username: Int
//
//            // Validate the token
//            //Decode username and password
//            val usernameAndPassword = String(Base64.getDecoder().decode(encodedUserPassword.toByteArray()))
//
//            //Split username and password tokens
//            val tokenizer = StringTokenizer(usernameAndPassword, ":")
//            username = Integer.parseInt(tokenizer.nextToken())
//            val password = tokenizer.nextToken()
//
//            println("usernameAndPassword: " + usernameAndPassword)
//
//            val user = retrieveRedis(username)
//
//            if (user.isPresent && user.get().token.equals(encodedUserPassword)) {
//                println("authentication success")
//                val authentication = UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities())
//                SecurityContextHolder.getContext().authentication = authentication
//            } else {
//                chain.doFilter(request, response)
//            }
//        }
//    }
//
//    private fun getAsHttpRequest(request: ServletRequest): HttpServletRequest {
//        if (request !is HttpServletRequest) {
//            throw RuntimeException("Expecting an HTTP request")
//        }
//
//        return request
//    }
//
//    private fun retrieveRedis(userid: Int): Optional<User> {
//        println("token: $userid")
//        val retrievedStudent = tokenService!!.findById(userid.toString())
//        println("token: $retrievedStudent")
//        return retrievedStudent
//    }
//
//
//}