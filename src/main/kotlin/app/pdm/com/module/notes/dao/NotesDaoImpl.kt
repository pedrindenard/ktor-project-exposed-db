package app.pdm.com.module.notes.dao

import app.pdm.com.module.notes.models.NotesResponse
import app.pdm.com.module.notes.models.NotesTable
import app.pdm.com.plugins.Database.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NotesDaoImpl : NotesDao {

    private fun resultRowToNotes(row: ResultRow) = NotesResponse(
        id = row[NotesTable.id],
        title = row[NotesTable.title],
        description = row[NotesTable.description],
    )

    override suspend fun getAllNotes(): List<NotesResponse> = dbQuery {
        NotesTable.selectAll().map(::resultRowToNotes)
    }

    override suspend fun getNote(id: Int): NotesResponse? = dbQuery {
        NotesTable.select { NotesTable.id eq id }.map(::resultRowToNotes).singleOrNull()
    }

    override suspend fun addNote(title: String, description: String): NotesResponse? = dbQuery {
        val insertStatement = NotesTable.insert {
            it[NotesTable.title] = title
            it[NotesTable.description] = description
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToNotes)
    }

    override suspend fun editNote(id: Int, title: String, description: String): Boolean = dbQuery {
        NotesTable.update({ NotesTable.id eq id }) {
            it[NotesTable.title] = title
            it[NotesTable.description] = description
        } > 0
    }

    override suspend fun deleteNote(id: Int): Boolean = dbQuery {
        NotesTable.deleteWhere { NotesTable.id eq id } > 0
    }

    companion object {
        val notesDao: NotesDao = NotesDaoImpl()
    }
}