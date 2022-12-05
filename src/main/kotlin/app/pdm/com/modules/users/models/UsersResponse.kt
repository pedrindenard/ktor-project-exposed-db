package app.pdm.com.modules.users.models

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val id: Int,
    val username: String,
    val email: String
)