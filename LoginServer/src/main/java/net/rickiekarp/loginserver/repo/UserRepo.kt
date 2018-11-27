package net.rickiekarp.loginserver.repo

import net.rickiekarp.loginserver.dao.UserDAO
import net.rickiekarp.foundation.model.Credentials
import net.rickiekarp.foundation.model.User
import net.rickiekarp.foundation.utils.DatabaseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.*

import javax.sql.DataSource

@Repository
open class UserRepo : UserDAO {
    private val FIND_BY_TOKEN = "SELECT * FROM credentials WHERE token = ?"
    private val FIND_BY_NAME = "SELECT * FROM credentials WHERE username = ?"
    private val INSERT = "INSERT INTO credentials(username, password, enabled) VALUES(?, ?, true)"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getUserFromToken(token: String): User? {
        var userVO: User? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_BY_TOKEN, Statement.RETURN_GENERATED_KEYS)
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
        userVO.userId = resultSet.getInt("id")
        userVO.username = resultSet.getString("username")
        userVO.password = resultSet.getString("password")
        return userVO
    }

    override fun updateUserToken(user: User, token: String) {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement("UPDATE credentials SET token = '" + token + "' WHERE id = " + user.userId)
            stmt.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }

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
                newUser.userId = resultSet.getInt(1)
            } else {
                println("There is no result when adding a new user!")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return newUser
    }
}
