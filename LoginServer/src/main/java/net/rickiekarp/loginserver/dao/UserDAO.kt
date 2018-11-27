package net.rickiekarp.loginserver.dao

import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User

interface UserDAO {
    fun getUserFromToken(token: String): User?
    fun getUserByName(username: String): User?
    fun updateUserToken(user: User, token: String)
    fun registerUser(user: Credentials): User?
}
