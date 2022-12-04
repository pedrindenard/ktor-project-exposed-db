package app.pdm.com.module.users.dao

import app.pdm.com.module.users.models.UsersResponse

interface UsersDao {

    suspend fun getUser(id: Int): UsersResponse?
    suspend fun getUserByEmail(email: String): UsersResponse?

    suspend fun addUser(username: String, email: String, password: String): UsersResponse?
    suspend fun editUser(id: Int, username: String, email: String, password: String): Boolean

    suspend fun deleteUser(id: Int): Boolean
}