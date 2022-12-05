package app.pdm.com.plugins

import com.auth0.jwt.exceptions.TokenExpiredException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
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

            exception<TokenExpiredException> { call, cause ->
                call.respond(message = cause.message.toString(), status = HttpStatusCode.Unauthorized)
            }

            exception<ExposedSQLException> { call, cause ->
                call.respond(message = cause.message.toString(), status = HttpStatusCode.UpgradeRequired)
            }
        }
    }
}