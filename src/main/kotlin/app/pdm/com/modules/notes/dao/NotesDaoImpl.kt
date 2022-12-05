package app.pdm.com.modules.notes.dao

import app.pdm.com.modules.notes.models.NotesResponse
import app.pdm.com.modules.notes.models.NotesTable
import app.pdm.com.modules.notes.repository.NotesRepository
import app.pdm.com.modules.notes.repository.NotesRepositoryImpl
import app.pdm.com.modules.notes.use_case.*
import app.pdm.com.plugins.Database.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NotesDaoImpl : NotesDao {

    private fun resultRowToNotes(row: ResultRow) = NotesResponse(
        id = row[NotesTable.id],
        title = row[NotesTable.title],
        description = row[NotesTable.description],
    )

    override suspend fun getAllNotes(userId: Int): List<NotesResponse> = dbQuery {
        NotesTable.select { NotesTable.userId eq userId }.map(::resultRowToNotes)
    }

    override suspend fun getNote(id: Int, userId: Int): NotesResponse? = dbQuery {
        NotesTable.select { NotesTable.id.eq(id) and NotesTable.userId.eq(userId) }.map(::resultRowToNotes).singleOrNull()
    }

    override suspend fun addNote(userId: Int, title: String, description: String): NotesResponse? = dbQuery {
        val insertStatement = NotesTable.insert {
            it[NotesTable.title] = title
            it[NotesTable.description] = description
            it[NotesTable.userId] = userId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToNotes)
    }

    override suspend fun editNote(id: Int, userId: Int, title: String, description: String): Boolean = dbQuery {
        NotesTable.update({ NotesTable.id.eq(id) and NotesTable.userId.eq(userId) }) {
            it[NotesTable.title] = title
            it[NotesTable.description] = description
        } > 0
    }

    override suspend fun deleteNote(id: Int, userId: Int): Boolean = dbQuery {
        NotesTable.deleteWhere { NotesTable.id.eq(id) and NotesTable.userId.eq(userId) } > 0
    }

    companion object {
        private val notesDao: NotesDao = NotesDaoImpl()
        private val notesRepository: NotesRepository = NotesRepositoryImpl(notesDao)
        val addNoteUseCase: AddNoteUseCase = AddNoteUseCase(notesRepository)
        val editNoteUseCase: EditNoteUseCase = EditNoteUseCase(notesRepository)
        val getNoteUseCase: GetNoteUseCase = GetNoteUseCase(notesRepository)
        val getAllNotesUseCase: GetAllNoteUseCase = GetAllNoteUseCase(notesRepository)
        val deleteNoteUseCase: DeleteNoteUserCase = DeleteNoteUserCase(notesRepository)
    }
}