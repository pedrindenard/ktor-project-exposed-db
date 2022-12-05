package app.pdm.com.modules.notes.repository

import app.pdm.com.modules.server.models.BaseResponse

interface NotesRepository {

    suspend fun getAllNotes(userId: Int): BaseResponse<Any>
    suspend fun getNote(id: Int, userId: Int): BaseResponse<Any>

    suspend fun addNote(userId: Int, title: String, description: String): BaseResponse<Any>
    suspend fun editNote(id: Int, userId: Int, title: String, description: String): BaseResponse<Any>

    suspend fun deleteNote(id: Int, userId: Int): BaseResponse<Any>
}