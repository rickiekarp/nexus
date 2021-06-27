package com.projekt.appserver.dao

import com.projekt.appserver.dto.NoteDTO
import com.projekt.backend.config.database.DataSourceFactory
import com.projekt.backend.utils.DatabaseUtil
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class NoteDaoImpl : NoteDAO {

    companion object {
        private val FIND_BY_TOKEN = "SELECT * FROM note WHERE userid = ?"
        private val INSERT = "INSERT INTO note(title, content, userid, dateAdded) VALUES (?, ?, ?, ?);"
        private val UPDATE = "UPDATE note SET title = ?, content = ? WHERE id = ?;"
        private val REMOVE = "UPDATE note SET isDeleted = 1 WHERE id = ?;"
    }

    override fun getAllNotes(userId: Int): List<NoteDTO> {
        val noteList = ArrayList<NoteDTO>()
        var note: NoteDTO
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = DataSourceFactory.getAppConnection()
            stmt = conn!!.prepareStatement(FIND_BY_TOKEN, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setInt(1, userId)

            val rs = stmt.executeQuery()
            while (rs.next()) {
                if (!rs.getBoolean("isDeleted")) {
                    note = NoteDTO()
                    note.noteId = rs.getInt("id")
                    note.title = rs.getString("title")
                    note.content = rs.getString("content")
                    note.setUserId(rs.getInt("userid"))
                    note.dateAdded = rs.getDate("dateAdded")
                    noteList.add(note)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }
        return noteList
    }

    override fun add(noteDTO: NoteDTO): NoteDTO {
        val noteAddDate = Date(Calendar.getInstance().time.time)
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = DataSourceFactory.getAppConnection()
            stmt = conn!!.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, noteDTO.title)
            stmt.setString(2, noteDTO.content)
            stmt.setInt(3, noteDTO.userId())
            stmt.setDate(4, noteAddDate)

            stmt.execute()

            val resultSet = stmt.generatedKeys
            if (resultSet.next()) {
                noteDTO.noteId = resultSet.getInt(1)
                noteDTO.dateAdded = noteAddDate
            } else {
                println("There is no result when adding a new note!")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }

        return noteDTO
    }

    override fun update(noteDTO: NoteDTO): Boolean {
        var wasExecuted = false
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = DataSourceFactory.getAppConnection()
            stmt = conn!!.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setString(1, noteDTO.title)
            stmt.setString(2, noteDTO.content)
            stmt.setInt(3, noteDTO.noteId)

            stmt.execute()
            wasExecuted = true
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }
        return wasExecuted
    }

    override fun remove(noteDTO: NoteDTO): Boolean {
        var wasExecuted = false
        var conn: Connection? = null
        var stmt: PreparedStatement? = null
        try {
            conn = DataSourceFactory.getAppConnection()
            stmt = conn!!.prepareStatement(REMOVE, Statement.RETURN_GENERATED_KEYS)
            stmt!!.setInt(1, noteDTO.noteId)

            stmt.execute()
            wasExecuted = true
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            DatabaseUtil.close(stmt)
            DatabaseUtil.close(conn)
        }
        return wasExecuted
    }
}