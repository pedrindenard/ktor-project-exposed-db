package app.pdm.com.module.users.repository

import app.pdm.com.module.server.models.BaseResponse
import app.pdm.com.module.users.models.UsersResponse

interface UsersRepository {

    suspend fun getUser(id: Int, user: UsersResponse): BaseResponse<Any>

    suspend fun addUser(username: String, email: String, password: String): BaseResponse<Any>
    suspend fun editUser(id: Int, username: String, email: String, password: String, user: UsersResponse): BaseResponse<Any>

    suspend fun deleteUser(id: Int, user: UsersResponse): BaseResponse<Any>
}