package app.pdm.com

import app.pdm.com.module.notes.NotesRouting.configureNoteRouting
import app.pdm.com.module.server.ServerRouting.configureServerRouting
import app.pdm.com.module.users.UsersRouting.configureUserRouting
import app.pdm.com.plugins.Database.configureDatabase
import app.pdm.com.plugins.Serializable.configureSerializable
import app.pdm.com.plugins.StatusPage.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::modules).start(wait = true)
}

fun Application.modules() {

    /* Plugins instance */
    configureDatabase()
    configureSerializable()
    configureStatusPages()

    /* Routes configuration */
    configureServerRouting()
    configureNoteRouting()
    configureUserRouting()
}