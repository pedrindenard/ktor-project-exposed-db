package app.pdm.com.module.users.models

import kotlinx.serialization.Serializable

@Serializable
data class UsersReceive(
    val username: String,
    val email: String,
    val password: String
)