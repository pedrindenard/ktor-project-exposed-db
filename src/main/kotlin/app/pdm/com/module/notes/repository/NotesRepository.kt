package app.pdm.com.module.notes.repository

import app.pdm.com.module.server.models.BaseResponse

interface NotesRepository {

    suspend fun getAllNotes(): BaseResponse<Any>
    suspend fun getNote(id: Int): BaseResponse<Any>

    suspend fun addNote(title: String, description: String): BaseResponse<Any>
    suspend fun editNote(id: Int, title: String, description: String): BaseResponse<Any>

    suspend fun deleteNote(id: Int): BaseResponse<Any>
}