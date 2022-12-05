package app.pdm.com.modules.notes.use_case

import app.pdm.com.modules.notes.repository.NotesRepository
import app.pdm.com.modules.server.models.BaseResponse
import app.pdm.com.utils.Utils.getIdFromBearerToken
import io.ktor.http.*
import io.ktor.server.auth.jwt.*

class GetNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(id: String, principal: JWTPrincipal?): BaseResponse<Any> {
        return when {
            principal != null && principal.getIdFromBearerToken == -1 -> {
                BaseResponse.Error(
                    message = "Invalid bearer token",
                    code = HttpStatusCode.Unauthorized
                )
            }
            else -> {
                try {
                    repository.getNote(id.toInt(), principal!!.getIdFromBearerToken)
                } catch (e: NumberFormatException) {
                    BaseResponse.Failure(e, HttpStatusCode.BadRequest)
                }
            }
        }

    }
}