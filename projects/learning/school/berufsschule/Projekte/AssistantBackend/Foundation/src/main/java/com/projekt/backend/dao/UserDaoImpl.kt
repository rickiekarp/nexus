package com.projekt.backend.dao

import com.projekt.backend.config.database.DataSourceFactory
import com.projekt.backend.model.Credentials
import com.projekt.backend.model.User
import com.projekt.backend.utils.DatabaseUtil
import java.sql.*

class UserDaoImpl : UserDAO {
    companion object {
        private val FIND_BY_TOKEN = "SELECT * FROM user WHERE token = ?"
        private val FIND_BY_NAME = "SELECT * FROM user WHERE username = ?"
        private val INSERT = "INSERT INTO user(username, password, enabled) VALUES(?, ?, true)"
    }

    override fun getUserFromToken(token: String): User? {
        var userVO: User? = null
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = DataSourceFactory.getLoginConnection()
            stmt = conn!!.prepareStatement(FIND_BY_TOKEN, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, token)

            val rs = stmt.executeQuery()
            if (rs.next()) {
                userVO = User()
                userVO.userId = rs.getInt("id")
                userVO.username = rs.getString("username")
                userVO.password = rs.getString("password")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }
        return userVO
    }

    override fun getUserByName(username: String): User? {
        val conn: Connection
        val stmt: PreparedStatement
        var userVO: User? = null
        try {
            conn = DataSourceFactory.getLoginConnection()
            stmt = conn.prepareStatement(FIND_BY_NAME, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, username)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                userVO = extractUserFromResultSet(rs)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return userVO
    }

    @Throws(SQLException::class)
    private fun extractUserFromResultSet(resultSet: ResultSet): User {
        val userVO = User()
        userVO.userId = resultSet.getInt("id")
        userVO.username = resultSet.getString("username")
        userVO.password = resultSet.getString("password")
        return userVO
    }

    override fun updateUserToken(user: User, token: String) {
        try {
            val ps = DataSourceFactory.getLoginConnection().prepareStatement("UPDATE user SET token = '" + token + "' WHERE id = " + user.userId)
            ps.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun registerUser(credentials: Credentials): User? {
        return createUser(credentials)
    }

    /**
     * @param credentials Registration credentials
     * @return User ID of the new user
     */
    private fun createUser(credentials: Credentials): User? {
        var newUser: User? = null
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = DataSourceFactory.getLoginConnection()
            stmt = conn!!.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, credentials.username)
            stmt.setString(2, credentials.password)

            stmt.execute()

            val resultSet = stmt.generatedKeys
            if (resultSet.next()) {
                newUser = User()
                newUser.userId = resultSet.getInt(1)
            } else {
                println("There is no result when adding a new user!")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }
        return newUser
    }
}