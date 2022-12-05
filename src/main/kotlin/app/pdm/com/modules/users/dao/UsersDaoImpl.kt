package app.pdm.com.modules.users.dao

import app.pdm.com.modules.login.models.LoginResponse
import app.pdm.com.modules.users.models.UsersResponse
import app.pdm.com.modules.users.models.UsersEntity
import app.pdm.com.plugins.Database.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UsersDaoImpl : UsersDao {

    override suspend fun getUser(id: Int): UsersResponse? = dbQuery {
        UsersEntity.select { UsersEntity.id eq id }.map(::resultRowToUsers).singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): LoginResponse? = dbQuery {
        UsersEntity.select { UsersEntity.email eq email.lowercase() }.map(::resultRowToLogin).firstOrNull()
    }

    override suspend fun addUser(username: String, email: String, password: String): UsersResponse? = dbQuery {
        val insertStatement = UsersEntity.insert {
            it[UsersEntity.username] = username
            it[UsersEntity.email] = email.lowercase()
            it[UsersEntity.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUsers)
    }

    override suspend fun editUser(id: Int, username: String, email: String, password: String): Boolean = dbQuery {
        UsersEntity.update({ UsersEntity.id eq id }) {
            it[UsersEntity.username] = username
            it[UsersEntity.email] = email.lowercase()
            it[UsersEntity.password] = password
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        UsersEntity.deleteWhere { UsersEntity.id eq id } > 0
    }

    private fun resultRowToUsers(row: ResultRow) = UsersResponse(
        id = row[UsersEntity.id],
        username = row[UsersEntity.username],
        email = row[UsersEntity.email]
    )

    private fun resultRowToLogin(row: ResultRow) = LoginResponse(
        id = row[UsersEntity.id],
        username = row[UsersEntity.username],
        email = row[UsersEntity.email],
        password = row[UsersEntity.password]
    )
}