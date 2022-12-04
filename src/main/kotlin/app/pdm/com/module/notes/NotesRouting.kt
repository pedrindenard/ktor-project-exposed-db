package app.pdm.com.module.notes

import app.pdm.com.module.notes.dao.NotesDaoImpl.Companion.notesRepository
import app.pdm.com.module.notes.models.NotesReceive
import app.pdm.com.utils.BaseResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object NotesRouting {

    fun Application.configureNoteRouting() = routing {
        route(path = "/notes") {

            get(path = "/getAll") {
                when (val response = notesRepository.getAllNotes()) {
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

            get(path = "/{id}") {
                val id = call.parameters["id"]!!.toInt()
                when (val response = notesRepository.getNote(id)) {
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
                val body = call.receive<NotesReceive>()
                when (val response = notesRepository.addNote(body.title, body.description)) {
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
                val body = call.receive<NotesReceive>()
                when (val response = notesRepository.editNote(id, body.title, body.description)) {
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
                when (val response = notesRepository.deleteNote(id)) {
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