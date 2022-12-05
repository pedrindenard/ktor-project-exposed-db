package app.pdm.com.modules.users.dao

import app.pdm.com.modules.login.models.LoginResponse
import app.pdm.com.modules.users.models.UsersResponse

interface UsersDao {

    suspend fun getUser(id: Int): UsersResponse?
    suspend fun getUserByEmail(email: String): LoginResponse?

    suspend fun addUser(username: String, email: String, password: String): UsersResponse?
    suspend fun editUser(id: Int, username: String, email: String, password: String): Boolean

    suspend fun deleteUser(id: Int): Boolean
}