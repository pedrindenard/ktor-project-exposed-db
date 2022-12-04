package app.pdm.com.module.users.dao

import app.pdm.com.module.users.models.UsersResponse
import app.pdm.com.module.users.models.UsersTable
import app.pdm.com.module.users.repository.UsersRepository
import app.pdm.com.module.users.repository.UsersRepositoryImpl
import app.pdm.com.plugins.Database.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UsersDaoImpl : UsersDao {

    private fun resultRowToUsers(row: ResultRow) = UsersResponse(
        id = row[UsersTable.id],
        username = row[UsersTable.username],
        email = row[UsersTable.email]
    )

    override suspend fun getUser(id: Int): UsersResponse? = dbQuery {
        UsersTable.select { UsersTable.id eq id }.map(::resultRowToUsers).singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): UsersResponse? = dbQuery {
        UsersTable.select { UsersTable.email eq email }.map(::resultRowToUsers).firstOrNull()
    }

    override suspend fun addUser(username: String, email: String, password: String): UsersResponse? = dbQuery {
        val insertStatement = UsersTable.insert {
            it[UsersTable.username] = username
            it[UsersTable.email] = email
            it[UsersTable.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUsers)
    }

    override suspend fun editUser(id: Int, username: String, email: String, password: String): Boolean = dbQuery {
        UsersTable.update({ UsersTable.id eq id }) {
            it[UsersTable.username] = username
            it[UsersTable.email] = email
            it[UsersTable.password] = password
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        UsersTable.deleteWhere { UsersTable.id eq id } > 0
    }

    companion object {
        private val usersDao: UsersDao = UsersDaoImpl()
        val usersRepository: UsersRepository = UsersRepositoryImpl(usersDao)
    }
}