package net.rickiekarp.admin.repo

import com.fasterxml.jackson.module.kotlin.readValue
import net.rickiekarp.admin.dao.ResumeDAO
import net.rickiekarp.admin.dto.ResumeDTO
import net.rickiekarp.admin.dto.ResumeDescriptionDTO
import net.rickiekarp.admin.dto.SkillsDTO
import net.rickiekarp.foundation.utils.DatabaseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource
import com.fasterxml.jackson.databind.ObjectMapper



@Repository
open class ResumeRepo : ResumeDAO {
    private val FIND_ACTIVE = "SELECT * FROM skill WHERE active = 1"
    private val FIND_RESUME = "select e.experience_id, e.startDate, e.endDate, c.name, j.title, e.description from experience e " +
            "join company c ON c.company_id = e.companyid " +
            "join job j ON j.job_id = e.jobid " +
            "where c.type = '%s' order by e.experience_id desc"

    @Autowired
    private val dataSource: DataSource? = null

    @Autowired
    private val jacksonObjectMapper: ObjectMapper? = null

    override fun getExperienceData(): List<ResumeDTO> {
        val list = ArrayList<ResumeDTO>()
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(findResumeString("experience"), Statement.RETURN_GENERATED_KEYS)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val taskDescription: ResumeDescriptionDTO = jacksonObjectMapper!!.readValue(rs.getString("description"))
                val user = ResumeDTO(
                        rs.getInt("experience_id"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("name"),
                        rs.getString("title"),
                        taskDescription
                )
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

    override fun getEducationData(): List<ResumeDTO> {
        val list = ArrayList<ResumeDTO>()
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(findResumeString("education"), Statement.RETURN_GENERATED_KEYS)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val taskDescription: ResumeDescriptionDTO = jacksonObjectMapper!!.readValue(rs.getString("description"))

                val user = ResumeDTO(
                        rs.getInt("experience_id"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("name"),
                        rs.getString("title"),
                        taskDescription
                )
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

    override fun getSkillsData(): List<SkillsDTO> {
        val list = ArrayList<SkillsDTO>(15)
        var stmt: PreparedStatement? = null
        try {
            stmt = dataSource!!.connection.prepareStatement(FIND_ACTIVE, Statement.RETURN_GENERATED_KEYS)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                val user = SkillsDTO()
                user.id = rs.getInt("skill_id")
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

    private fun findResumeString(s: String): String {
        return String.format(FIND_RESUME, s)
    }
}
