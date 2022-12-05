package app.pdm.com.modules.notes.use_case

import app.pdm.com.modules.notes.models.NotesReceive
import app.pdm.com.modules.notes.repository.NotesRepository
import app.pdm.com.modules.server.models.BaseResponse
import app.pdm.com.utils.Utils.getIdFromBearerToken
import io.ktor.http.*
import io.ktor.server.auth.jwt.*

class AddNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(note: NotesReceive, principal: JWTPrincipal?): BaseResponse<Any> {
        return when {
            principal != null && principal.getIdFromBearerToken == -1 -> {
                BaseResponse.Error(
                    message = "Invalid bearer token",
                    code = HttpStatusCode.Unauthorized
                )
            }
            note.title.isBlank() || note.description.isBlank() -> {
                BaseResponse.Error(
                    message = "Title and description cannot be empty.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            else -> {
                repository.addNote(principal!!.getIdFromBearerToken, note.title, note.description)
            }
        }
    }
}