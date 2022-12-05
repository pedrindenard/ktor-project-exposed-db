package app.pdm.com.modules.login.use_case

import app.pdm.com.modules.login.models.LoginReceive
import app.pdm.com.modules.login.repository.LoginRepository
import app.pdm.com.modules.server.models.BaseResponse
import app.pdm.com.utils.Utils.isInvalidEmail
import app.pdm.com.utils.Utils.isInvalidPassword
import io.ktor.http.*

class LoginUserCase(private val repository: LoginRepository) {

    suspend operator fun invoke(login: LoginReceive): BaseResponse<Any> {
        return when {
            login.email.isBlank() || login.password.isBlank() -> {
                BaseResponse.Error(
                    message = "Email and password cannot be empty.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            login.email.isInvalidEmail() -> {
                BaseResponse.Error(
                    message = "Email must be valid.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            login.password.isInvalidPassword() -> {
                BaseResponse.Error(
                    message = "Password must contain an uppercase and lowercase letter, a number, a special character and be at least 8 characters long.",
                    code = HttpStatusCode.NotAcceptable
                )
            }
            else -> {
                repository.doUserLogin(login)
            }
        }
    }
}