package app.pdm.com.module.notes.use_case

import app.pdm.com.module.notes.repository.NotesRepository
import app.pdm.com.module.server.models.BaseResponse

class GetAllNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(): BaseResponse<Any> {
        return repository.getAllNotes()
    }
}