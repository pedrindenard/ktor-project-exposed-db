package app.pdm.com.plugins

import app.pdm.com.modules.notes.models.NotesEntity
import app.pdm.com.modules.users.models.UsersEntity
import app.pdm.com.utils.Environment
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    @Suppress(names = ["unused"])
    fun Application.configureDatabase() {
        val database = Database.connect(Environment.jdbcURL, Environment.driverClassName)

        transaction(database) {
            SchemaUtils.create(NotesEntity)
            SchemaUtils.create(UsersEntity)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}