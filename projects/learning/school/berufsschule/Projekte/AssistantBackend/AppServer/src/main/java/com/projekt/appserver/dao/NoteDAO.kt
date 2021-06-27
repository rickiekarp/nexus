package com.projekt.appserver.dao

import com.projekt.appserver.dto.NoteDTO

interface NoteDAO {
    fun getAllNotes(primaryKey: Int): List<NoteDTO>
    fun add(noteDTO: NoteDTO): NoteDTO
    fun update(noteDTO: NoteDTO): Boolean
    fun remove(noteDTO: NoteDTO): Boolean
}
