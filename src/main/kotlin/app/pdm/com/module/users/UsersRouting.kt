package app.pdm.com.module.users

import app.pdm.com.module.server.models.BaseResponse
import app.pdm.com.module.users.dao.UsersDaoImpl.Companion.addUserUseCase
import app.pdm.com.module.users.dao.UsersDaoImpl.Companion.deleteUserUseCase
import app.pdm.com.module.users.dao.UsersDaoImpl.Companion.editUserUseCase
import app.pdm.com.module.users.dao.UsersDaoImpl.Companion.getUserUseCase
import app.pdm.com.module.users.models.UsersReceive
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

                get(path = "/{id}") {
                    val id = call.parameters["id"].toString()
                    val principal = call.principal<JWTPrincipal>()
                    when (val response = getUserUseCase.invoke(id, principal)) {
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

                post(path = "/edit/{id}") {
                    val id = call.parameters["id"].toString()
                    val principal = call.principal<JWTPrincipal>()
                    val body = call.receive<UsersReceive>()
                    when (val response = editUserUseCase.invoke(id, body, principal)) {
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

                delete(path = "/delete/{id}") {
                    val id = call.parameters["id"].toString()
                    val principal = call.principal<JWTPrincipal>()
                    when (val response = deleteUserUseCase.invoke(id, principal)) {
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