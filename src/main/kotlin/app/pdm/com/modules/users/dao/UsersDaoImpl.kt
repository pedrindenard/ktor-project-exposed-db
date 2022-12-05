package app.pdm.com.modules.users.dao

import app.pdm.com.modules.login.models.LoginResponse
import app.pdm.com.modules.login.repository.LoginRepositoryImpl
import app.pdm.com.modules.login.use_case.LoginUserCase
import app.pdm.com.modules.users.models.UsersResponse
import app.pdm.com.modules.users.models.UsersTable
import app.pdm.com.modules.users.repository.UsersRepository
import app.pdm.com.modules.users.repository.UsersRepositoryImpl
import app.pdm.com.modules.users.use_case.AddUserUseCase
import app.pdm.com.modules.users.use_case.DeleteUserUserCase
import app.pdm.com.modules.users.use_case.EditUserUseCase
import app.pdm.com.modules.users.use_case.GetUserUseCase
import app.pdm.com.plugins.Database.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UsersDaoImpl : UsersDao {

    override suspend fun getUser(id: Int): UsersResponse? = dbQuery {
        UsersTable.select { UsersTable.id eq id }.map(::resultRowToUsers).singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): LoginResponse? = dbQuery {
        UsersTable.select { UsersTable.email eq email.lowercase() }.map(::resultRowToLogin).firstOrNull()
    }

    override suspend fun addUser(username: String, email: String, password: String): UsersResponse? = dbQuery {
        val insertStatement = UsersTable.insert {
            it[UsersTable.username] = username
            it[UsersTable.email] = email.lowercase()
            it[UsersTable.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUsers)
    }

    override suspend fun editUser(id: Int, username: String, email: String, password: String): Boolean = dbQuery {
        UsersTable.update({ UsersTable.id eq id }) {
            it[UsersTable.username] = username
            it[UsersTable.email] = email.lowercase()
            it[UsersTable.password] = password
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        UsersTable.deleteWhere { UsersTable.id eq id } > 0
    }

    private fun resultRowToUsers(row: ResultRow) = UsersResponse(
        id = row[UsersTable.id],
        username = row[UsersTable.username],
        email = row[UsersTable.email]
    )

    private fun resultRowToLogin(row: ResultRow) = LoginResponse(
        id = row[UsersTable.id],
        username = row[UsersTable.username],
        email = row[UsersTable.email],
        password = row[UsersTable.password]
    )

    companion object {
        private val usersDao: UsersDao = UsersDaoImpl()
        private val usersRepository: UsersRepository = UsersRepositoryImpl(usersDao)
        private val loginRepository: LoginRepositoryImpl = LoginRepositoryImpl(usersDao)
        val addUserUseCase: AddUserUseCase = AddUserUseCase(usersRepository)
        val editUserUseCase: EditUserUseCase = EditUserUseCase(usersRepository)
        val getUserUseCase: GetUserUseCase = GetUserUseCase(usersRepository)
        val deleteUserUseCase: DeleteUserUserCase = DeleteUserUserCase(usersRepository)
        val loginUseCase: LoginUserCase = LoginUserCase(loginRepository)
    }
}