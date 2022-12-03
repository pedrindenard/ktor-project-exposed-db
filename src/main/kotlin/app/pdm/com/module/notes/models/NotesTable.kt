package app.pdm.com.module.notes.models

import org.jetbrains.exposed.sql.Table

object NotesTable : Table() {
    val id = integer(name = "id").autoIncrement()
    val title = varchar(name = "title", length = 128)
    val description = varchar(name = "description", length = 1024)

    override val primaryKey = PrimaryKey(id)
}