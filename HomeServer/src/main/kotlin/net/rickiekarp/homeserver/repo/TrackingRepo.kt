package net.rickiekarp.homeserver.repo

import net.rickiekarp.foundation.utils.DatabaseUtil
import net.rickiekarp.homeserver.dao.TrackingDao
import net.rickiekarp.homeserver.dto.WeightDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

@Repository
open class TrackingRepo : TrackingDao {

    private val SELECT_WEIGHT_HISTORY = "select * from tracking_weight where users_id = ? order by id desc limit ?"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getWeightHistory(userId: Int, limit: Int): List<WeightDto> {
        val notesList = ArrayList<WeightDto>()

        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(SELECT_WEIGHT_HISTORY)
            stmt!!.setInt(1, userId)
            stmt.setInt(2, limit)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                val userVO = WeightDto()
                userVO.id = rs.getInt("id")
                userVO.userId = rs.getInt("users_id")
                userVO.dateAdded = rs.getDate("dateAdded")
                userVO.weight = rs.getDouble("weight")
                userVO.description = rs.getString("description")
                notesList.add(userVO)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return notesList
    }

}
