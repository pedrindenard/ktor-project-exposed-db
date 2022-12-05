package app.pdm.com.modules.users

import app.pdm.com.modules.server.models.BaseResponse
import app.pdm.com.core.Injection.addUserUseCase
import app.pdm.com.core.Injection.deleteUserUseCase
import app.pdm.com.core.Injection.editUserUseCase
import app.pdm.com.core.Injection.getUserUseCase
import app.pdm.com.modules.users.models.UsersReceive
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object UsersRouting {

    fun Application.configureUserRouting() = routing {
        route(path = "/users") {

            post(path = "/add") {
                val body = call.receive<UsersReceive>()
                when (val response = addUserUseCase.invoke(body)) {
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

            authenticate {

                get(path = "/info") {
                    val principal = call.principal<JWTPrincipal>()
                    when (val response = getUserUseCase.invoke(principal)) {
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

                put(path = "/edit") {
                    val principal = call.principal<JWTPrincipal>()
                    val body = call.receive<UsersReceive>()
                    when (val response = editUserUseCase.invoke(body, principal)) {
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

                delete(path = "/delete") {
                    val principal = call.principal<JWTPrincipal>()
                    when (val response = deleteUserUseCase.invoke(principal)) {
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
}