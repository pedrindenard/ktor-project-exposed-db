package app.pdm.com.modules.users.use_case

import app.pdm.com.modules.server.models.BaseResponse
import app.pdm.com.modules.users.repository.UsersRepository
import app.pdm.com.utils.Utils.getIdFromBearerToken
import io.ktor.http.*
import io.ktor.server.auth.jwt.*

class DeleteUserUserCase(private val repository: UsersRepository) {

    suspend operator fun invoke(principal: JWTPrincipal?): BaseResponse<Any> {
        return when {
            principal != null && principal.getIdFromBearerToken == -1 -> {
                BaseResponse.Error(
                    message = "Invalid bearer token",
                    code = HttpStatusCode.Unauthorized
                )
            }
            else -> {
                try {
                    repository.deleteUser(principal!!.getIdFromBearerToken)
                } catch (e: NumberFormatException) {
                    BaseResponse.Failure(e, HttpStatusCode.BadRequest)
                }
            }
        }
    }
}