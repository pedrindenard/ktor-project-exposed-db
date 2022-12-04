package app.pdm.com.module.users

import app.pdm.com.module.users.dao.UsersDaoImpl.Companion.usersRepository
import app.pdm.com.module.users.models.UsersReceive
import app.pdm.com.utils.BaseResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object UsersRouting {

    fun Application.configureUserRouting() = routing {
        route(path = "/users") {

            get(path = "/{id}") {
                val id = call.parameters["id"]!!.toInt()
                when (val response = usersRepository.getUser(id)) {
                    is BaseResponse.Error -> {
                        call.respond(response.code, response.message)
                    }
                    is BaseResponse.Failure -> {
                        call.respond(response.code, response.throwable)
                    }
                    is BaseResponse.Success -> {
                        call.respond(response.code, response.data)
                    }
                }
            }

            post(path = "/add") {
                val body = call.receive<UsersReceive>()
                when (val response = usersRepository.addUser(body.username, body.email, body.hashPassword())) {
                    is BaseResponse.Error -> {
                        call.respond(response.code, response.message)
                    }
                    is BaseResponse.Failure -> {
                        call.respond(response.code, response.throwable)
                    }
                    is BaseResponse.Success -> {
                        call.respond(response.code, response.data)
                    }
                }
            }

            post(path = "/edit/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val body = call.receive<UsersReceive>()
                when (val response = usersRepository.editUser(id, body.username, body.email, body.hashPassword())) {
                    is BaseResponse.Error -> {
                        call.respond(response.code, response.message)
                    }
                    is BaseResponse.Failure -> {
                        call.respond(response.code, response.throwable)
                    }
                    is BaseResponse.Success -> {
                        call.respond(response.code, response.data)
                    }
                }
            }

            delete(path = "/delete/{id}") {
                val id = call.parameters["id"]!!.toInt()
                when (val response = usersRepository.deleteUser(id)) {
                    is BaseResponse.Error -> {
                        call.respond(response.code, response.message)
                    }
                    is BaseResponse.Failure -> {
                        call.respond(response.code, response.throwable)
                    }
                    is BaseResponse.Success -> {
                        call.respond(response.code, response.data)
                    }
                }
            }
        }
    }
}