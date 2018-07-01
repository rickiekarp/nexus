package com.rkarp.foundation.model

//import com.fasterxml.jackson.annotation.JsonIgnore
import java.security.Principal

class User : Credentials, Principal {
    var userId: Int = 0
    var role: List<String>? = null

    constructor() {
        //empty
    }

    constructor(username: String, password: String) : super() {
        this.username = username
        this.password = password
    }

//    @JsonIgnore
    override fun getName(): String? {
        return username
    }
}