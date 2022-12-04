package app.pdm.com.module.users.repository

import app.pdm.com.module.server.models.BaseResponse

interface UsersRepository {

    suspend fun getUser(id: Int): BaseResponse<Any>

    suspend fun addUser(username: String, email: String, password: String): BaseResponse<Any>
    suspend fun editUser(id: Int, username: String, email: String, password: String): BaseResponse<Any>

    suspend fun deleteUser(id: Int): BaseResponse<Any>
}