package app.pdm.com.module.server

import app.pdm.com.module.server.models.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object ServerRouting {

    fun Application.configureServerRouting() = routing {
        get(path = "/") {
            call.respond(HttpStatusCode.OK, MessageResponse("Welcome to my first API on KTOR!"))
        }
    }
}