package app.pdm.com.modules.notes.models

import kotlinx.serialization.Serializable

@Serializable
data class NotesReceive(
    val title: String,
    val description: String
)