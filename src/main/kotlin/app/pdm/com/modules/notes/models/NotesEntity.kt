package app.pdm.com.modules.notes.models

import app.pdm.com.modules.users.models.UsersEntity
import org.jetbrains.exposed.sql.Table

object NotesEntity : Table() {
    val id = integer(name = "id").autoIncrement()
    val userId = integer(name = "user_id").references(UsersEntity.id)
    val title = varchar(name = "title", length = 128)
    val description = varchar(name = "description", length = 1024)

    override val primaryKey = PrimaryKey(id)
}