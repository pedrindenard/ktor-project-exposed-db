package app.pdm.com.module.users.use_case

import app.pdm.com.module.server.models.BaseResponse
import app.pdm.com.module.users.repository.UsersRepository
import app.pdm.com.utils.Utils.getUserFromBearerToken
import io.ktor.http.*
import io.ktor.server.auth.jwt.*

class GetUserUseCase(private val repository: UsersRepository) {

    suspend operator fun invoke(id: String, principal: JWTPrincipal?): BaseResponse<Any> {
        return when {
            principal != null && principal.getUserFromBearerToken().id == -1 -> {
                BaseResponse.Error(
                    message = "Invalid bearer token",
                    code = HttpStatusCode.Unauthorized
                )
            }
            else -> {
                try {
                    repository.getUser(id.toInt(), principal!!.getUserFromBearerToken())
                } catch (e: NumberFormatException) {
                    BaseResponse.Failure(e, HttpStatusCode.BadRequest)
                }
            }
        }
    }
}