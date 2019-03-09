package net.rickiekarp.admin.repo

import net.rickiekarp.admin.dao.ResumeDAO
import net.rickiekarp.admin.dto.SkillsDTO
import net.rickiekarp.foundation.utils.DatabaseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
open class ResumeRepo : ResumeDAO {

    private val FIND_ACTIVE = "SELECT * FROM skill WHERE active = 1"

    @Autowired
    private val dataSource: DataSource? = null

    override fun getSkillsData(): List<SkillsDTO> {
        val list = ArrayList<SkillsDTO>(15)
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_ACTIVE, Statement.RETURN_GENERATED_KEYS)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val user = SkillsDTO()
                user.id = rs.getInt("id")
                user.text = rs.getString("text")
                list.add(user)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(dataSource!!.connection)
        }
        return list
    }
}
