package app.pdm.com.modules.notes

import app.pdm.com.modules.notes.dao.NotesDaoImpl.Companion.addNoteUseCase
import app.pdm.com.modules.notes.dao.NotesDaoImpl.Companion.deleteNoteUseCase
import app.pdm.com.modules.notes.dao.NotesDaoImpl.Companion.editNoteUseCase
import app.pdm.com.modules.notes.dao.NotesDaoImpl.Companion.getAllNotesUseCase
import app.pdm.com.modules.notes.dao.NotesDaoImpl.Companion.getNoteUseCase
import app.pdm.com.modules.notes.models.NotesReceive
import app.pdm.com.modules.server.models.BaseResponse
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object NotesRouting {

    fun Application.configureNoteRouting() = routing {
        route(path = "/notes") {

            authenticate {

                get(path = "/getAll") {
                    val principal = call.principal<JWTPrincipal>()
                    when (val response = getAllNotesUseCase.invoke(principal)) {
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

                get(path = "/{id}") {
                    val id = call.parameters["id"].toString()
                    val principal = call.principal<JWTPrincipal>()
                    when (val response = getNoteUseCase.invoke(id, principal)) {
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

                post(path = "/add") {
                    val body = call.receive<NotesReceive>()
                    val principal = call.principal<JWTPrincipal>()
                    when (val response = addNoteUseCase.invoke(body, principal)) {
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

                put(path = "/edit/{id}") {
                    val id = call.parameters["id"].toString()
                    val principal = call.principal<JWTPrincipal>()
                    val body = call.receive<NotesReceive>()
                    when (val response = editNoteUseCase.invoke(id, body, principal)) {
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
                    when (val response = deleteNoteUseCase.invoke(id, principal)) {
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