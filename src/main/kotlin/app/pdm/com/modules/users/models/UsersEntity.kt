package app.pdm.com.modules.users.models

import org.jetbrains.exposed.sql.Table

object UsersEntity : Table() {
    val id = integer(name = "id").autoIncrement()
    val username = varchar(name = "username", length = 128)
    val email = varchar(name = "email", length = 128)
    val password = varchar(name = "password", length = 128)

    override val primaryKey = PrimaryKey(id)
}