package app.pdm.com.modules.notes.dao

import app.pdm.com.modules.notes.models.NotesResponse

interface NotesDao {

    suspend fun getAllNotes(userId: Int): List<NotesResponse>
    suspend fun getNote(id: Int, userId: Int): NotesResponse?

    suspend fun addNote(userId: Int, title: String, description: String): NotesResponse?
    suspend fun editNote(id: Int, userId: Int, title: String, description: String): Boolean

    suspend fun deleteNote(id: Int, userId: Int): Boolean
}