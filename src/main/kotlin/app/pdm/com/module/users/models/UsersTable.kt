package app.pdm.com.module.users.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.lowerCase

object UsersTable : Table() {
    val id = integer(name = "id").autoIncrement()
    val username = varchar(name = "username", length = 128)
    val email = varchar(name = "email", length = 128)
    val password = varchar(name = "password", length = 128)

    override val primaryKey = PrimaryKey(id)
}