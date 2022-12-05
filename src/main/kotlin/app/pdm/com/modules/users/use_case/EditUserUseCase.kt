package app.pdm.com.modules.users.use_case

import app.pdm.com.modules.server.models.BaseResponse
import app.pdm.com.modules.users.models.UsersReceive
import app.pdm.com.modules.users.repository.UsersRepository
import app.pdm.com.utils.Utils.getIdFromBearerToken
import app.pdm.com.utils.Utils.hashPassword
import app.pdm.com.utils.Utils.isInvalidEmail
import app.pdm.com.utils.Utils.isInvalidPassword
import io.ktor.http.*
import io.ktor.server.auth.jwt.*

class EditUserUseCase(private val repository: UsersRepository) {

    suspend operator fun invoke(user: UsersReceive, principal: JWTPrincipal?): BaseResponse<Any> {
        return when {
            principal != null && principal.getIdFromBearerToken == -1 -> {
                BaseResponse.Error(
                    message = "Invalid bearer token",
                    code = HttpStatusCode.Unauthorized
                )
            }
            user.username.isBlank() || user.email.isBlank() || user.password.isBlank() -> {
                BaseResponse.Error(
                    message = "Username, email and password cannot be empty.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            user.email.isInvalidEmail() -> {
                BaseResponse.Error(
                    message = "Email must be valid.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            user.password.isInvalidPassword() -> {
                BaseResponse.Error(
                    message = "Password must contain an uppercase and lowercase letter, a number, a special character and be at least 8 characters long.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            else -> {
                try {
                    repository.editUser(
                        principal!!.getIdFromBearerToken,
                        user.username,
                        user.email,
                        user.password.hashPassword()
                    )
                } catch (e: NumberFormatException) {
                    BaseResponse.Failure(e, HttpStatusCode.BadRequest)
                }
            }
        }
    }
}