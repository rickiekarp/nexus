package com.projekt.appserver.dto

import java.io.Serializable
import java.sql.Date

class NoteDTO : Serializable {
    var noteId: Int = 0
    var title: String? = null
    var content: String? = null
    private var userId: Int = 0
    var dateAdded: Date? = null

    fun userId(): Int {
        return userId
    }

    fun setUserId(userId: Int) {
        this.userId = userId
    }
}