package com.projekt.backend.security

import com.projekt.backend.model.User
import java.security.Principal
import javax.ws.rs.core.SecurityContext

/**
 * Custom Security Context.
 */
class CustomSecurityContext internal constructor(private val user: User, private val scheme: String) : SecurityContext {

    override fun getUserPrincipal(): Principal {
        return this.user
    }

    override fun isUserInRole(s: String): Boolean {
        return user.role != null && user.role!!.contains(s)
    }

    override fun isSecure(): Boolean {
        return "https" == this.scheme
    }

    override fun getAuthenticationScheme(): String {
        return SecurityContext.BASIC_AUTH
    }
}