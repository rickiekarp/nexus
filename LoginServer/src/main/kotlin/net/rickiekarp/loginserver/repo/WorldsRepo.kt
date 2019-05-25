package net.rickiekarp.loginserver.repo

import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.loginserver.dao.WorldsDAO
import net.rickiekarp.loginserver.domain.World
import net.rickiekarp.loginserver.domain.WorldList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
open class WorldsRepo : WorldsDAO {
    private val FIND_ALL = "SELECT * FROM world"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getWorldList(): WorldList {
        val worldList = WorldList.newBuilder()
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_ALL, Statement.RETURN_GENERATED_KEYS)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val world = World
                        .newBuilder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setUrl(rs.getString("url"))
                        .setWorldstatusid(rs.getInt("worldstatus_id"))
                        .build()
                worldList.addWorld(world)
            }
        } catch (e: SQLException) {
            Log.DEBUG.error("Exception", e)
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return worldList.build()
    }

}
