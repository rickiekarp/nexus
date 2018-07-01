package com.rkarp.foundation.dao

import com.rkarp.foundation.model.Credentials
import com.rkarp.foundation.model.User

interface UserDAO {
    fun getUserFromToken(token: String): User?
    fun getUserByName(username: String): User?
    fun updateUserToken(user: User, token: String)
    fun registerUser(user: Credentials): User?
}
