package app.pdm.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import javax.naming.AuthenticationException

object StatusPage {

    fun Application.configureStatusPages() {
        install(StatusPages) {
            status(HttpStatusCode.NotFound) { call, _ ->
                call.respond(message = "Request does not match any route!", status = HttpStatusCode.NotFound)
            }

            exception<AuthenticationException> { call, cause ->
                call.respond(message = cause.message.toString(), status = HttpStatusCode.Unauthorized)
            }

            exception<NumberFormatException> { call, cause ->
                call.respond(message = cause.message.toString(), status = HttpStatusCode.BadRequest)
            }

            exception<NullPointerException> { call, cause ->
                call.respond(message = cause.message.toString(), status = HttpStatusCode.InternalServerError)
            }

            exception<BadRequestException> { call, cause ->
                call.respond(message = cause.message.toString(), status = HttpStatusCode.BadRequest)
            }
        }
    }
}