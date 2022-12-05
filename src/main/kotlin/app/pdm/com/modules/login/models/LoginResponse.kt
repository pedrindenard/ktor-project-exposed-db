package app.pdm.com.modules.login.models

import app.pdm.com.modules.users.models.UsersResponse
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