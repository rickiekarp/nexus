package net.rickiekarp.loginserver.repo

import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.loginserver.dto.ApplicationDTO
import net.rickiekarp.loginserver.dao.UpdateDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import javax.sql.DataSource

@Repository
class UpdateRepo : UpdateDAO {
    private val FIND_BY_IDENTIFIER = "SELECT * FROM application WHERE identifier=? OR identifier='appupdater'"
    private val INSERT = "INSERT INTO application(identifier, version, updateEnable) VALUES(?, ?, ?)"
    private val UPDATE = "UPDATE application SET version=?, updateEnable=? WHERE identifier=?"

    @Autowired
    private val dataSource: DataSource? = null

    override fun findByName(identifier: String, updateChannel: Int): List<ApplicationDTO> {
        var stmt: PreparedStatement? = null
        val list = ArrayList<ApplicationDTO>()
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_BY_IDENTIFIER)
            stmt!!.setString(1, identifier)

            val rs = stmt.executeQuery()

            while (rs.next()) {
                val user = ApplicationDTO()
                user.identifier = rs.getString("identifier")
                user.version = rs.getInt("version")
                user.isUpdateEnable = rs.getBoolean("updateEnable")

                list.add(user)
            }
        } catch (e: SQLException) {
            // e.printStackTrace();
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }

        return list
    }

    override fun insert(application: ApplicationDTO): Int {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, application.identifier)
            stmt.setInt(2, application.version)
            stmt.setBoolean(3, application.isUpdateEnable)

            return stmt.executeUpdate()
        } catch (e: SQLException) {
            // e.printStackTrace();
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
    }

    override fun update(application: ApplicationDTO): Int {
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(UPDATE)
            stmt!!.setInt(1, application.version)
            stmt.setBoolean(2, application.isUpdateEnable)
            stmt.setString(3, application.identifier)

            return stmt.executeUpdate()
        } catch (e: SQLException) {
            // e.printStackTrace();
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
    }
}
