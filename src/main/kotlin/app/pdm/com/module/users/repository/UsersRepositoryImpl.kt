package app.pdm.com.module.users.repository

import app.pdm.com.module.users.dao.UsersDao
import app.pdm.com.module.server.models.BaseResponse
import io.ktor.http.*

class UsersRepositoryImpl(private val dao: UsersDao) : UsersRepository {

    override suspend fun getUser(id: Int): BaseResponse<Any> {
        val user = dao.getUser(id)

        return if (user != null) {
            BaseResponse.Success(data = user, code = HttpStatusCode.OK)
        } else {
            BaseResponse.Error(message = "User not found.", code = HttpStatusCode.NotFound)
        }
    }

    override suspend fun addUser(username: String, email: String, password: String): BaseResponse<Any> {
        return if (isEmailExistsInDatabase(email)) {
            BaseResponse.Error(message = "Email already registered.", code = HttpStatusCode.NotAcceptable)
        } else {
            val user = dao.addUser(username, email, password)

            if (user != null) {
                BaseResponse.Success(data = user, code = HttpStatusCode.Created)
            } else {
                BaseResponse.Error(message = "User not added.", code = HttpStatusCode.BadRequest)
            }
        }
    }

    override suspend fun editUser(id: Int, username: String, email: String, password: String): BaseResponse<Any> {
        return if (isEmailExistsInDatabase(email)) {
            BaseResponse.Error(message = "Email already registered.", code = HttpStatusCode.NotAcceptable)
        } else {
            val userAdded = dao.editUser(id, username, email, password)
            val user = dao.getUser(id)

            if (userAdded && user != null) {
                BaseResponse.Success(data = user, code = HttpStatusCode.Accepted)
            } else {
                BaseResponse.Error(message = "User not found.", code = HttpStatusCode.NotFound)
            }
        }
    }

    override suspend fun deleteUser(id: Int): BaseResponse<Any> {
        val userDeleted = dao.deleteUser(id)

        return if (userDeleted) {
            BaseResponse.Success(data = "User deleted successfully.", code = HttpStatusCode.OK)
        } else {
            BaseResponse.Error(message = "User not found.", code = HttpStatusCode.NotFound)
        }
    }

    private suspend fun isEmailExistsInDatabase(email: String): Boolean {
        return dao.getUserByEmail(email) != null
    }
}