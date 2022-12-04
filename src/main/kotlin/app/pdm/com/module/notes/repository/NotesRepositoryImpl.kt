package app.pdm.com.module.notes.repository

import app.pdm.com.module.notes.dao.NotesDao
import app.pdm.com.module.server.models.BaseResponse
import io.ktor.http.*

class NotesRepositoryImpl(private val dao: NotesDao) : NotesRepository {

    override suspend fun getAllNotes(): BaseResponse<Any> {
        return BaseResponse.Success(data = dao.getAllNotes(), code = HttpStatusCode.OK)
    }

    override suspend fun getNote(id: Int): BaseResponse<Any> {
        val note = dao.getNote(id)

        return if (note != null) {
            BaseResponse.Success(data = note, code = HttpStatusCode.OK)
        } else {
            BaseResponse.Error(message = "Note not found.", code = HttpStatusCode.NotFound)
        }
    }

    override suspend fun addNote(title: String, description: String): BaseResponse<Any> {
        val note = dao.addNote(title, description)

        return if (note != null) {
            BaseResponse.Success(data = note, code = HttpStatusCode.Created)
        } else {
            BaseResponse.Error(message = "Note not added.", code = HttpStatusCode.BadRequest)
        }
    }

    override suspend fun editNote(id: Int, title: String, description: String): BaseResponse<Any> {
        val noteAdded = dao.editNote(id, title, description)
        val note = dao.getNote(id)

        return if (noteAdded && note != null) {
            BaseResponse.Success(data = note, code = HttpStatusCode.Accepted)
        } else {
            BaseResponse.Error(message = "Note not found.", code = HttpStatusCode.NotFound)
        }
    }

    override suspend fun deleteNote(id: Int): BaseResponse<Any> {
        val noteDeleted = dao.deleteNote(id)

        return if (noteDeleted) {
            BaseResponse.Success(data = "Note deleted successfully.", code = HttpStatusCode.OK)
        } else {
            BaseResponse.Error(message = "Note not found.", code = HttpStatusCode.NotFound)
        }
    }
}