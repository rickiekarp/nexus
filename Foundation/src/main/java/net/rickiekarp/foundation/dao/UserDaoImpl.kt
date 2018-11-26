package net.rickiekarp.foundation.dao

import net.rickiekarp.foundation.config.database.DataSourceFactory
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.foundation.utils.DatabaseUtil
import java.sql.*

class UserDaoImpl : net.rickiekarp.foundation.dao.UserDAO {
    companion object {
        private val FIND_BY_TOKEN = "SELECT * FROM credentials WHERE token = ?"
        private val FIND_BY_NAME = "SELECT * FROM credentials WHERE username = ?"
        private val INSERT = "INSERT INTO credentials(username, password, enabled) VALUES(?, ?, true)"
    }

    override fun getUserFromToken(token: String): User? {
        var userVO: User? = null
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = net.rickiekarp.foundation.config.database.DataSourceFactory.getLoginConnection()
            stmt = conn!!.prepareStatement(net.rickiekarp.foundation.dao.UserDaoImpl.Companion.FIND_BY_TOKEN, Statement.RETURN_GENERATED_KEYS)
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
            conn = net.rickiekarp.foundation.config.database.DataSourceFactory.getLoginConnection()
            stmt = conn.prepareStatement(net.rickiekarp.foundation.dao.UserDaoImpl.Companion.FIND_BY_NAME, Statement.RETURN_GENERATED_KEYS)
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
            val ps = net.rickiekarp.foundation.config.database.DataSourceFactory.getLoginConnection().prepareStatement("UPDATE credentials SET token = '" + token + "' WHERE id = " + user.userId)
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
            conn = net.rickiekarp.foundation.config.database.DataSourceFactory.getLoginConnection()
            stmt = conn!!.prepareStatement(net.rickiekarp.foundation.dao.UserDaoImpl.Companion.INSERT, Statement.RETURN_GENERATED_KEYS)
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