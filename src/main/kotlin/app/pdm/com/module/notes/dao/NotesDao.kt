package app.pdm.com.module.notes.dao

import app.pdm.com.module.notes.models.NotesResponse

interface NotesDao {

    suspend fun getAllNotes(): List<NotesResponse>
    suspend fun getNote(id: Int): NotesResponse?

    suspend fun addNote(title: String, description: String): NotesResponse?
    suspend fun editNote(id: Int, title: String, description: String): Boolean

    suspend fun deleteNote(id: Int): Boolean
}