package net.rickiekarp.foundation.model

import org.springframework.data.redis.core.RedisHash
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.ArrayList
import org.springframework.security.core.GrantedAuthority

@RedisHash("token")
class User : Credentials {
    var id: Int = 0
    var token: String? = null
    var role: Byte = 0

    constructor() {
        //empty
    }

    constructor(username: String, password: String) : super() {
        this.username = username
        this.password = password
    }

//    @JsonIgnore
    fun getName(): String? {
        return username
    }

    fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities = ArrayList<SimpleGrantedAuthority>()
//        for (role in this.getUserRoles()) {
//            authorities.add(SimpleGrantedAuthority("ROLE_" + role.getRole()))
//        }
        authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        return authorities
    }

    override fun toString(): String {
        return "User(id=$id, token='$token', role=$role)"
    }
}