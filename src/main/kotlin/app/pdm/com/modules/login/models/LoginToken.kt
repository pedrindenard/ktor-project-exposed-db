package app.pdm.com.modules.login.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginToken(
    val token: String
)