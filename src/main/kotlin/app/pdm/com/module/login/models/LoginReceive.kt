package app.pdm.com.module.login.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceive(
    val email: String,
    val password: String
)