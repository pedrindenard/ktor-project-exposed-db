package app.pdm.com.module.notes.models

import kotlinx.serialization.Serializable

@Serializable
data class NotesResponse(
    val id: Int,
    val title: String,
    val description: String
)