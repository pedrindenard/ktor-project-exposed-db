package app.pdm.com.module.server.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val message: String
)