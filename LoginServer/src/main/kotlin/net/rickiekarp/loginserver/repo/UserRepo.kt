package net.rickiekarp.loginserver.repo

import net.rickiekarp.foundation.config.redis.TokenRepository
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.loginserver.dao.UserDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
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

    @Autowired
    private val dataSource: DataSource? = null

    @Autowired
    private val redisRepository: TokenRepository? = null

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
            stmt.setString(2, user.password)
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
}
