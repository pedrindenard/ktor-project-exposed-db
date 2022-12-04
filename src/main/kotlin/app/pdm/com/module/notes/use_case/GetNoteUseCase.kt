package app.pdm.com.module.notes.use_case

import app.pdm.com.module.notes.repository.NotesRepository
import app.pdm.com.module.server.models.BaseResponse
import io.ktor.http.*

class GetNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(id: String): BaseResponse<Any> {
        return try {
            repository.getNote(id.toInt())
        } catch (e: NumberFormatException) {
            BaseResponse.Failure(e, HttpStatusCode.BadRequest)
        }
    }
}