package app.pdm.com.module.login.models

import app.pdm.com.module.users.models.UsersResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val password: String
) {

    val response: UsersResponse
        get() = UsersResponse(
            id = id,
            username = username,
            email = email
        )
}