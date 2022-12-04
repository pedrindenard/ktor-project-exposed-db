package app.pdm.com.module.notes

import app.pdm.com.module.notes.dao.NotesDaoImpl.Companion.addNoteUseCase
import app.pdm.com.module.notes.dao.NotesDaoImpl.Companion.deleteNoteUseCase
import app.pdm.com.module.notes.dao.NotesDaoImpl.Companion.editNoteUseCase
import app.pdm.com.module.notes.dao.NotesDaoImpl.Companion.getAllNotesUseCase
import app.pdm.com.module.notes.dao.NotesDaoImpl.Companion.getNoteUseCase
import app.pdm.com.module.notes.models.NotesReceive
import app.pdm.com.module.server.models.BaseResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object NotesRouting {

    fun Application.configureNoteRouting() = routing {
        route(path = "/notes") {

            get(path = "/getAll") {
                when (val response = getAllNotesUseCase.invoke()) {
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
                val id = call.parameters["id"].toString()
                when (val response = getNoteUseCase.invoke(id)) {
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
                when (val response = addNoteUseCase.invoke(body)) {
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
                val id = call.parameters["id"].toString()
                val body = call.receive<NotesReceive>()
                when (val response = editNoteUseCase.invoke(id, body)) {
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
                val id = call.parameters["id"].toString()
                when (val response = deleteNoteUseCase.invoke(id)) {
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