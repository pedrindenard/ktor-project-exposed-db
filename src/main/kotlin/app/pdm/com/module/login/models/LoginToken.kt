package app.pdm.com.module.login.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginToken(
    val token: String
)