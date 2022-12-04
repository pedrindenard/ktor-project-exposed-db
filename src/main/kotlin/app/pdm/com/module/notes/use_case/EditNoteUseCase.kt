package app.pdm.com.module.notes.use_case

import app.pdm.com.module.notes.models.NotesReceive
import app.pdm.com.module.notes.repository.NotesRepository
import app.pdm.com.module.server.models.BaseResponse
import io.ktor.http.*

class EditNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(id: String, note: NotesReceive): BaseResponse<Any> {
        return when {
            note.title.isBlank() || note.description.isBlank() -> {
                BaseResponse.Error(
                    message = "Title and description cannot be empty.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            else -> {
                try {
                    repository.editNote(id.toInt(), note.title, note.description)
                } catch (e: NumberFormatException) {
                    BaseResponse.Failure(e, HttpStatusCode.BadRequest)
                }
            }
        }
    }
}