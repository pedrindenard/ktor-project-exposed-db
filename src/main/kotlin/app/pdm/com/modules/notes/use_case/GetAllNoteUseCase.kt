package app.pdm.com.modules.notes.use_case

import app.pdm.com.modules.notes.repository.NotesRepository
import app.pdm.com.modules.server.models.BaseResponse
import app.pdm.com.utils.Utils.getIdFromBearerToken
import io.ktor.http.*
import io.ktor.server.auth.jwt.*

class GetAllNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(principal: JWTPrincipal?): BaseResponse<Any> {
        return if (principal != null && principal.getIdFromBearerToken == -1) {
            BaseResponse.Error(
                message = "Invalid bearer token",
                code = HttpStatusCode.Unauthorized
            )
        } else {
            repository.getAllNotes(principal!!.getIdFromBearerToken)
        }
    }
}