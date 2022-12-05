package app.pdm.com.modules.login.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceive(
    val email: String,
    val password: String
)