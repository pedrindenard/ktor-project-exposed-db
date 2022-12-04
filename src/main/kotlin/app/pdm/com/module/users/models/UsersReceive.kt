package app.pdm.com.module.users.models

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UsersReceive(
    val username: String,
    val email: String,
    val password: String
) {

    fun hashPassword(): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}