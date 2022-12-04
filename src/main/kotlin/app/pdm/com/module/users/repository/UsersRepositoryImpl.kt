package app.pdm.com.module.users.repository

import app.pdm.com.module.server.models.BaseResponse
import app.pdm.com.module.users.dao.UsersDao
import app.pdm.com.module.users.models.UsersResponse
import io.ktor.http.*

class UsersRepositoryImpl(private val dao: UsersDao) : UsersRepository {

    override suspend fun getUser(id: Int, user: UsersResponse): BaseResponse<Any> {
        val response = dao.getUser(id)

        return when {
            response != null && user.email == response.email -> {
                BaseResponse.Success(data = response, code = HttpStatusCode.OK)
            }
            response == null -> {
                BaseResponse.Error(message = "User not found.", code = HttpStatusCode.NotFound)
            }
            else -> {
                BaseResponse.Error(message = "Invalid user ID", code = HttpStatusCode.BadRequest)
            }
        }
    }

    override suspend fun addUser(username: String, email: String, password: String): BaseResponse<Any> {
        return if (isEmailExistsInDatabase(email)) {
            BaseResponse.Error(message = "Email already registered.", code = HttpStatusCode.NotAcceptable)
        } else {
            val response = dao.addUser(username, email, password)

            if (response != null) {
                BaseResponse.Success(data = response, code = HttpStatusCode.Created)
            } else {
                BaseResponse.Error(message = "User not added.", code = HttpStatusCode.BadRequest)
            }
        }
    }

    override suspend fun editUser(id: Int, username: String, email: String, password: String, user: UsersResponse): BaseResponse<Any> {
        return when {
            isEmailExistsInDatabase(email) -> {
                BaseResponse.Error(message = "Email already registered.", code = HttpStatusCode.NotAcceptable)
            }
            isEmailsEquals(id, user.email) -> {
                val userAdded = dao.editUser(id, username, email, password)
                val response = dao.getUser(id)

                if (userAdded && response != null) {
                    BaseResponse.Success(data = response, code = HttpStatusCode.Accepted)
                } else {
                    BaseResponse.Error(message = "User not found.", code = HttpStatusCode.NotFound)
                }
            }
            else -> {
                BaseResponse.Error(message = "Invalid user ID", code = HttpStatusCode.BadRequest)
            }
        }
    }

    override suspend fun deleteUser(id: Int, user: UsersResponse): BaseResponse<Any> {
        return when {
            isEmailsEquals(id, user.email) -> {
                val response = dao.deleteUser(id)

                if (response) {
                    BaseResponse.Success(data = "User deleted successfully.", code = HttpStatusCode.OK)
                } else {
                    BaseResponse.Error(message = "User not found.", code = HttpStatusCode.NotFound)
                }
            }
            else -> {
                BaseResponse.Error(message = "Invalid user ID", code = HttpStatusCode.BadRequest)
            }
        }
    }

    private suspend fun isEmailExistsInDatabase(email: String): Boolean {
        return dao.getUserByEmail(email) != null
    }

    private suspend fun isEmailsEquals(id: Int, email: String): Boolean {
        val user = dao.getUser(id)
        return user != null && user.email == email
    }
}