package com.projekt.backend.dao

import com.projekt.backend.model.Credentials
import com.projekt.backend.model.User

interface UserDAO {
    fun getUserFromToken(token: String): User?
    fun getUserByName(username: String): User?
    fun updateUserToken(user: User, token: String)
    fun registerUser(user: Credentials): User?
}
