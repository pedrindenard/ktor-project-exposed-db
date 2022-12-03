package app.pdm.com.plugins

import app.pdm.com.module.notes.models.NotesTable
import app.pdm.com.module.users.models.UsersTable
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    @Suppress(names = ["unused"])
    fun Application.configureDatabase() {
        val environment = ApplicationConfig(configPath = null)

        val driverClassName = environment.propertyOrNull(path = "ktor.environment.driverClassName")!!.getString()
        val jdbcURL = environment.propertyOrNull(path = "ktor.environment.jdbcURL")!!.getString()

        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(NotesTable)
            SchemaUtils.create(UsersTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}