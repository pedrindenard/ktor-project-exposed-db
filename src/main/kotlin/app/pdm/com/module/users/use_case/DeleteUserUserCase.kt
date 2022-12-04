package app.pdm.com.module.users.use_case

import app.pdm.com.module.server.models.BaseResponse
import app.pdm.com.module.users.repository.UsersRepository
import io.ktor.http.*

class DeleteUserUserCase(private val repository: UsersRepository) {

    suspend operator fun invoke(id: String): BaseResponse<Any> {
        return try {
            repository.deleteUser(id.toInt())
        } catch (e: NumberFormatException) {
            BaseResponse.Failure(e, HttpStatusCode.BadRequest)
        }
    }
}