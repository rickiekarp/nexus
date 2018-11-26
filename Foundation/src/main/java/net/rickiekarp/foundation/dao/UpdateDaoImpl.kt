package net.rickiekarp.foundation.dao

import net.rickiekarp.foundation.utils.DatabaseUtil
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class UpdateDaoImpl : net.rickiekarp.foundation.dao.UpdateDAO {
    companion object {
        private val FIND_BY_IDENTIFIER = "SELECT * FROM application WHERE identifier=? OR identifier='appupdater'"

        private val INSERT = "INSERT INTO application(identifier, version, updateEnable) VALUES(?, ?, ?)"
        private val UPDATE = "UPDATE application SET version=?, updateEnable=? WHERE identifier=?"
    }

    override fun findAll(): List<net.rickiekarp.foundation.dao.entity.ApplicationEntity> {
        return ArrayList()
    }

    override fun findById(): List<net.rickiekarp.foundation.dao.entity.ApplicationEntity> {
        return ArrayList()
    }

    override fun findByName(identifier: String, updateChannel: Int): List<net.rickiekarp.foundation.dao.entity.ApplicationEntity> {
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        val list = ArrayList<net.rickiekarp.foundation.dao.entity.ApplicationEntity>()
        try {
            conn = net.rickiekarp.foundation.config.database.DataSourceFactory.getAppConnection()
            stmt = conn!!.prepareStatement(net.rickiekarp.foundation.dao.UpdateDaoImpl.Companion.FIND_BY_IDENTIFIER)
            stmt!!.setString(1, identifier)

            val rs = stmt.executeQuery()

            while (rs.next()) {
                val user = net.rickiekarp.foundation.dao.entity.ApplicationEntity()
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
            DatabaseUtil.close(conn)
        }

        return list
    }

    override fun insert(application: net.rickiekarp.foundation.dao.entity.ApplicationEntity): Int {
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = net.rickiekarp.foundation.config.database.DataSourceFactory.getAppConnection()
            stmt = conn!!.prepareStatement(net.rickiekarp.foundation.dao.UpdateDaoImpl.Companion.INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, application.identifier)
            stmt.setInt(2, application.version)
            stmt.setBoolean(3, application.isUpdateEnable)

            return stmt.executeUpdate()
        } catch (e: SQLException) {
            // e.printStackTrace();
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }
    }

    override fun update(application: net.rickiekarp.foundation.dao.entity.ApplicationEntity): Int {
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = net.rickiekarp.foundation.config.database.DataSourceFactory.getAppConnection()
            stmt = conn!!.prepareStatement(net.rickiekarp.foundation.dao.UpdateDaoImpl.Companion.UPDATE)
            stmt!!.setInt(1, application.version)
            stmt.setBoolean(2, application.isUpdateEnable)
            stmt.setString(3, application.identifier)

            return stmt.executeUpdate()
        } catch (e: SQLException) {
            // e.printStackTrace();
            throw RuntimeException(e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }
    }
}
