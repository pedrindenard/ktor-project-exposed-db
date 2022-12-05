package app.pdm.com.modules.notes.dao

import app.pdm.com.modules.notes.models.NotesResponse
import app.pdm.com.modules.notes.models.NotesEntity
import app.pdm.com.plugins.Database.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NotesDaoImpl : NotesDao {

    override suspend fun getAllNotes(userId: Int): List<NotesResponse> = dbQuery {
        NotesEntity.select { NotesEntity.userId eq userId }.map(::resultRowToNotes)
    }

    override suspend fun getNote(id: Int, userId: Int): NotesResponse? = dbQuery {
        NotesEntity.select { NotesEntity.id.eq(id) and NotesEntity.userId.eq(userId) }.map(::resultRowToNotes).singleOrNull()
    }

    override suspend fun addNote(userId: Int, title: String, description: String): NotesResponse? = dbQuery {
        val insertStatement = NotesEntity.insert {
            it[NotesEntity.title] = title
            it[NotesEntity.description] = description
            it[NotesEntity.userId] = userId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToNotes)
    }

    override suspend fun editNote(id: Int, userId: Int, title: String, description: String): Boolean = dbQuery {
        NotesEntity.update({ NotesEntity.id.eq(id) and NotesEntity.userId.eq(userId) }) {
            it[NotesEntity.title] = title
            it[NotesEntity.description] = description
        } > 0
    }

    override suspend fun deleteNote(id: Int, userId: Int): Boolean = dbQuery {
        NotesEntity.deleteWhere { NotesEntity.id.eq(id) and NotesEntity.userId.eq(userId) } > 0
    }

    private fun resultRowToNotes(row: ResultRow) = NotesResponse(
        id = row[NotesEntity.id],
        title = row[NotesEntity.title],
        description = row[NotesEntity.description],
    )
}