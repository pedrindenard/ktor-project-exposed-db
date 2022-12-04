package app.pdm.com.module.login.repository

import app.pdm.com.module.login.models.LoginReceive
import app.pdm.com.module.server.models.BaseResponse
import app.pdm.com.module.users.dao.UsersDao
import app.pdm.com.utils.Utils.generateJWTToken
import app.pdm.com.utils.Utils.passwordNotMatch
import io.ktor.http.*

class LoginRepositoryImpl(private val dao: UsersDao) : LoginRepository {

    override suspend fun doUserLogin(login: LoginReceive): BaseResponse<Any> {
        val user = dao.getUserByEmail(login.email)

        return when {
            user == null -> {
                BaseResponse.Error(message = "Invalid email.", code = HttpStatusCode.Unauthorized)
            }
            user.password.passwordNotMatch(login.password) -> {
                BaseResponse.Error(message = "Invalid password.", code = HttpStatusCode.Unauthorized)
            }
            else -> {
                BaseResponse.Success(data = generateJWTToken(user.response), code = HttpStatusCode.Accepted)
            }
        }
    }
}