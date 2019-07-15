package net.rickiekarp.loginserver.repo

import net.rickiekarp.foundation.config.redis.TokenRepository
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.loginserver.dao.UserDAO
import net.rickiekarp.loginserver.utils.HashingUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
open class UserRepo : UserDAO {
    private val FIND_BY_TOKEN = "SELECT * FROM users u JOIN user_roles ur ON u.id = ur.users_id JOIN roles r ON r.id = ur.roles_id WHERE token = ?"
    private val FIND_BY_NAME = "SELECT * FROM users u JOIN user_roles ur ON u.id = ur.users_id JOIN roles r ON r.id = ur.roles_id WHERE username = ?"
    private val INSERT = "CALL createUser(?, ?, ?, true)"
    private val UPDATE = "UPDATE users SET token = ? WHERE id = ?"
    private val DO_LOGIN = "UPDATE login SET lastLoginDate = now(), lastLoginIP = ? WHERE users_id = ?"

    @Autowired
    private val dataSource: DataSource? = null

    @Autowired
    private val redisRepository: TokenRepository? = null

    @Autowired
    private val hashingUtil: HashingUtil? = null

    override fun getUserFromToken(token: String): User? {
        var userVO: User? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_BY_TOKEN, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, token)

            val rs = stmt.executeQuery()
            if (rs.next()) {
                userVO = extractUserFromResultSet(rs)
            }
        } catch (e: SQLException) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return userVO
    }

    override fun getUserByName(username: String): User? {
        var stmt: PreparedStatement? = null
        var userVO: User? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_BY_NAME, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, username)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                userVO = extractUserFromResultSet(rs)
            }
        } catch (e: Exception) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return userVO
    }

    @Throws(SQLException::class)
    private fun extractUserFromResultSet(resultSet: ResultSet): User {
        val userVO = User()
        userVO.id = resultSet.getInt("id")
        userVO.username = resultSet.getString("username")
        userVO.password = resultSet.getString("password")
        userVO.token = resultSet.getString("token")
        userVO.accountType = resultSet.getByte("type")
        userVO.isAccountEnabled = resultSet.getBoolean("enabled")
        userVO.role = Pair<Int, String>(resultSet.getInt("roles_id"), resultSet.getString("name"))
        return userVO
    }

    override fun updateUserToken(user: User, token: String) {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, token)
            stmt.setInt(2, user.id)
            stmt.executeUpdate()
        } catch (e: Exception) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }

        user.token = token
        Log.DEBUG.debug("adding to redis: $user")
        redisRepository!!.save(user)
    }

    override fun registerUser(user: Credentials): User? {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, user.username)
            stmt.setString(2, hashingUtil!!.generateStorngPasswordHash(user.password!!))
            stmt.setInt(3, 2) // user role (1 = ADMIN, 2 = USER)
            stmt.execute()
        } catch (e: SQLException) {
            //Log.DEBUG.error("Exception", e)
            Log.DEBUG.debug("User could not be created! Reason: " + e.message)
            return null
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return getUserByName(user.username!!)
    }

    override fun doLogin(userId: Int): Boolean {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val ip = request.remoteAddr

        var stmt: PreparedStatement? = null
        return try {
            stmt = dataSource!!.connection.prepareStatement(DO_LOGIN, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, ip)
            stmt.setInt(2, userId)
            stmt.execute()
            true
        } catch (e: SQLException) {
            Log.DEBUG.debug("Login currently not possible! Reason: " + e.message)
            false
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
    }
}
