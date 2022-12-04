package app.pdm.com.module.login

import app.pdm.com.module.login.models.LoginReceive
import app.pdm.com.module.server.models.BaseResponse
import app.pdm.com.module.users.dao.UsersDaoImpl.Companion.loginUseCase
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object LoginRouting {

    fun Application.configureLoginRouting() = routing {
        route(path = "/users") {

            post(path = "/login") {
                val body = call.receive<LoginReceive>()
                when (val response = loginUseCase.invoke(body)) {
                    is BaseResponse.Error -> {
                        call.respond(response.code, response.message)
                    }
                    is BaseResponse.Failure -> {
                        call.respond(response.code, response.throwable.toString())
                    }
                    is BaseResponse.Success -> {
                        call.respond(response.code, response.data)
                    }
                }
            }
        }
    }
}