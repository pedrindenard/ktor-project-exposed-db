package app.pdm.com.modules.server.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val message: String
)