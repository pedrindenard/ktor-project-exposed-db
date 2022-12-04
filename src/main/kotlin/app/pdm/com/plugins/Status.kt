package app.pdm.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import javax.naming.AuthenticationException

object Status {

    fun Application.configureStatusPages() {
        install(StatusPages) {
            exception<AuthenticationException> { call, cause ->
                call.respond(message = cause.message.toString(), status = HttpStatusCode.Unauthorized)
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