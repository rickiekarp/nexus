package net.rickiekarp.loginserver.repo

import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.loginserver.dao.WorldsDAO
import net.rickiekarp.loginserver.dto.WorldDTO
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

    override fun getWorldList(): List<WorldDTO> {
        val worldList = ArrayList<WorldDTO>()
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_ALL, Statement.RETURN_GENERATED_KEYS)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val world = WorldDTO()
                world.id = rs.getInt("id")
                world.name = rs.getString("name")
                world.url = rs.getString("url")
                world.worldstatus_id = rs.getInt("worldstatus_id")
                worldList.add(world)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return worldList
    }

}
