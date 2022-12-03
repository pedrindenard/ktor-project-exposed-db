package app.pdm.com.module.users

import app.pdm.com.module.server.models.MessageResponse
import app.pdm.com.module.users.dao.UsersDaoImpl.Companion.usersDao
import app.pdm.com.module.users.models.UsersReceive
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object UsersRouting {

    fun Application.configureUserRouting() = routing {
        /* GET NOT BY ID */
        get(path = "users/{id}") {
            val id = call.parameters["id"]!!.toInt()
            call.respond(HttpStatusCode.OK, usersDao.getUser(id)!!)
        }

        /* ADD NOTE */
        post(path = "users/add") {
            val body = call.receive<UsersReceive>()
            val note = usersDao.addUser(body.username, body.email, body.password)
            call.respond(HttpStatusCode.OK, note!!)
        }

        /* EDIT NOTE WHERE ID */
        post(path = "users/edit/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val body = call.receive<UsersReceive>()
            usersDao.editUser(id, body.username, body.email, body.password)
            call.respond(HttpStatusCode.OK, usersDao.getUser(id)!!)
        }

        /* DELETE NOTE WHERE ID */
        delete(path = "users/delete/{id}") {
            val id = call.parameters["id"]!!.toInt()
            usersDao.deleteUser(id)
            call.respond(HttpStatusCode.OK, MessageResponse("User deleted successfully."))
        }
    }
}