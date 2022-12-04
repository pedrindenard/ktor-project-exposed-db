package app.pdm.com.plugins

import app.pdm.com.utils.Environment
import app.pdm.com.utils.Utils.verifyJWTToken
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*

object Auth {

    fun Application.configureAuthentication() {
        install(Authentication) {
            jwt {
                verifier(verifyJWTToken())

                realm = Environment.realm

                validate { jwtCredential ->
                    if (
                        jwtCredential.payload.getClaim("username").asString().isNotEmpty() &&
                        jwtCredential.payload.getClaim("email").asString().isNotEmpty() &&
                        jwtCredential.payload.getClaim("id").asInt() != null
                    ) {
                        JWTPrincipal(jwtCredential.payload)
                    } else {
                        null
                    }
                }
            }
        }
    }
}