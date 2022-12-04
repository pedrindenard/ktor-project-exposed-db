package app.pdm.com.module.notes.use_case

import app.pdm.com.module.notes.models.NotesReceive
import app.pdm.com.module.notes.repository.NotesRepository
import app.pdm.com.module.server.models.BaseResponse
import io.ktor.http.*

class AddNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(note: NotesReceive): BaseResponse<Any> {
        return when {
            note.title.isBlank() || note.description.isBlank() -> {
                BaseResponse.Error(
                    message = "Title and description cannot be empty.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            else -> {
                repository.addNote(note.title, note.description)
            }
        }
    }
}