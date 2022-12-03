package app.pdm.com.module.notes

import app.pdm.com.module.notes.dao.NotesDaoImpl.Companion.notesDao
import app.pdm.com.module.notes.models.NotesReceive
import app.pdm.com.module.server.models.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object NotesRouting {

    fun Application.configureNoteRouting() = routing {

        /* GET ALL NOTES */
        get(path = "notes/getAll") {
            call.respond(HttpStatusCode.OK, notesDao.getAllNotes())
        }

        /* GET NOT BY ID */
        get(path = "notes/{id}") {
            val id = call.parameters["id"]!!.toInt()
            call.respond(HttpStatusCode.OK, notesDao.getNote(id)!!)
        }

        /* ADD NOTE */
        post(path = "notes/add") {
            val body = call.receive<NotesReceive>()
            val note = notesDao.addNote(body.title, body.description)
            call.respond(HttpStatusCode.OK, note!!)
        }

        /* EDIT NOTE WHERE ID */
        post(path = "notes/edit/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val body = call.receive<NotesReceive>()
            notesDao.editNote(id, body.title, body.description)
            call.respond(HttpStatusCode.OK, notesDao.getNote(id)!!)
        }

        /* DELETE NOTE WHERE ID */
        delete(path = "notes/delete/{id}") {
            val id = call.parameters["id"]!!.toInt()
            notesDao.deleteNote(id)
            call.respond(HttpStatusCode.OK, MessageResponse("Note deleted successfully."))
        }
    }
}