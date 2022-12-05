package app.pdm.com.modules.notes.repository

import app.pdm.com.modules.notes.dao.NotesDao
import app.pdm.com.modules.server.models.BaseResponse
import io.ktor.http.*

class NotesRepositoryImpl(private val dao: NotesDao) : NotesRepository {

    override suspend fun getAllNotes(userId: Int): BaseResponse<Any> {
        return BaseResponse.Success(data = dao.getAllNotes(userId), code = HttpStatusCode.OK)
    }

    override suspend fun getNote(id: Int, userId: Int): BaseResponse<Any> {
        val note = dao.getNote(id, userId)

        return if (note != null) {
            BaseResponse.Success(data = note, code = HttpStatusCode.OK)
        } else {
            BaseResponse.Error(message = "Note not found.", code = HttpStatusCode.NotFound)
        }
    }

    override suspend fun addNote(userId: Int, title: String, description: String): BaseResponse<Any> {
        val note = dao.addNote(userId, title, description)

        return if (note != null) {
            BaseResponse.Success(data = note, code = HttpStatusCode.Created)
        } else {
            BaseResponse.Error(message = "Note not added.", code = HttpStatusCode.BadRequest)
        }
    }

    override suspend fun editNote(id: Int, userId: Int, title: String, description: String): BaseResponse<Any> {
        val noteAdded = dao.editNote(id, userId, title, description)
        val note = dao.getNote(id, userId)

        return if (noteAdded && note != null) {
            BaseResponse.Success(data = note, code = HttpStatusCode.Accepted)
        } else {
            BaseResponse.Error(message = "Note not found.", code = HttpStatusCode.NotFound)
        }
    }

    override suspend fun deleteNote(id: Int, userId: Int): BaseResponse<Any> {
        val noteDeleted = dao.deleteNote(id, userId)

        return if (noteDeleted) {
            BaseResponse.Success(data = "Note deleted successfully.", code = HttpStatusCode.OK)
        } else {
            BaseResponse.Error(message = "Note not found.", code = HttpStatusCode.NotFound)
        }
    }
}