package net.rickiekarp.foundation.data.repository

import net.rickiekarp.foundation.data.dao.ApplicationSettingsDao
import net.rickiekarp.foundation.data.dto.ApplicationSettingDto
import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.utils.DatabaseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
open class ApplicationSettingsRepo: ApplicationSettingsDao {
    private val FIND_BY_IDENTIFIER = "SELECT * FROM applicationsettings WHERE identifier = ?"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getApplicationSettingByIdentifier(settingIdentifier: String): ApplicationSettingDto? {
        var userVO: ApplicationSettingDto? = null
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_BY_IDENTIFIER, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, settingIdentifier)

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

    @Throws(SQLException::class)
    private fun extractUserFromResultSet(resultSet: ResultSet): ApplicationSettingDto {
        val userVO = ApplicationSettingDto()
        userVO.id = resultSet.getInt("id")
        userVO.identifier = resultSet.getString("identifier")
        userVO.content = resultSet.getString("content")
        userVO.lastUpdated = resultSet.getDate("lastUpdated")
        return userVO
    }

}