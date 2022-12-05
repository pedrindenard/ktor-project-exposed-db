package app.pdm.com

import app.pdm.com.modules.login.LoginRouting.configureLoginRouting
import app.pdm.com.modules.notes.NotesRouting.configureNoteRouting
import app.pdm.com.modules.server.ServerRouting.configureServerRouting
import app.pdm.com.modules.users.UsersRouting.configureUserRouting
import app.pdm.com.plugins.Auth.configureAuthentication
import app.pdm.com.plugins.Database.configureDatabase
import app.pdm.com.plugins.Serializable.configureSerializable
import app.pdm.com.plugins.Status.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::modules).start(wait = true)
}

fun Application.modules() {

    /* Database instance */
    configureDatabase()

    /* Plugins implementation */
    configureAuthentication()
    configureSerializable()
    configureStatusPages()

    /* Routes configuration */
    configureServerRouting()
    configureNoteRouting()
    configureUserRouting()
    configureLoginRouting()
}