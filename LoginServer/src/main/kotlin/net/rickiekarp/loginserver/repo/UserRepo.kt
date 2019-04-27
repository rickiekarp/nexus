package net.rickiekarp.loginserver.repo

import net.rickiekarp.foundation.config.redis.TokenRepository
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
    private val FIND_BY_TOKEN = "SELECT * FROM users WHERE token = ?"
    private val FIND_BY_NAME = "SELECT * FROM users WHERE username = ?"
    private val INSERT = "INSERT INTO users(username, password, enabled) VALUES(?, ?, true)"
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
            e.printStackTrace()
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
            e.printStackTrace()
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
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }

        user.token = token
        println("adding to redis: $user")
        redisRepository!!.save(user)
    }

    override fun registerUser(user: Credentials): User? {
        var newUser: User? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, user.username)
            stmt.setString(2, user.password)

            stmt.execute()

            val resultSet = stmt.generatedKeys
            if (resultSet.next()) {
                newUser = User()
                newUser.id = resultSet.getInt(1)
            } else {
                println("There is no result when adding a new user!")
            }
        } catch (e: SQLException) {
            //e.printStackTrace()
            println("User could not be created! Reason: " + e.message)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return newUser
    }
}
