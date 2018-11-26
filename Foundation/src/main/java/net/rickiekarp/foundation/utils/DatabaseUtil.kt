package net.rickiekarp.foundation.utils

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class DatabaseUtil {
    companion object {
        fun close(con: Connection?) {
            if (con != null) {
                try {
                    con.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }

        fun close(stmt: Statement?) {
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
        }
    }
}