package app.pdm.com.utils

import app.pdm.com.modules.login.models.LoginToken
import app.pdm.com.modules.users.models.UsersResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import org.mindrot.jbcrypt.BCrypt
import java.util.*

object Utils {

    @JvmStatic
    private val EMAIL_REGEX = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"

    fun String.isInvalidEmail(): Boolean {
        return !EMAIL_REGEX.toRegex().matches(this)
    }

    fun String.isInvalidPassword(): Boolean {
        if (length < 8) return true
        if (firstOrNull { it.isDigit() } == null) return true
        if (filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null) return true
        if (filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return true
        if (firstOrNull { !it.isLetterOrDigit() } == null) return true

        return false
    }

    fun String.hashPassword(): String {
        return BCrypt.hashpw(this, BCrypt.gensalt())
    }

    fun String.passwordNotMatch(password: String): Boolean {
        return !BCrypt.checkpw(password, this)
    }

    fun generateJWTToken(user: UsersResponse): LoginToken {
        val jwtTokenExpiration = System.currentTimeMillis() + 10800000 /* 3 hours */

        val token = JWT.create()
            .withAudience(Environment.audience)
            .withIssuer(Environment.issuer)
            .withClaim("username", user.username)
            .withClaim("email", user.email)
            .withClaim("id", user.id)
            .withExpiresAt(Date(jwtTokenExpiration))
            .sign(Algorithm.HMAC256(Environment.secret))

        return LoginToken(token = token)
    }

    fun verifyJWTToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(Environment.secret))
            .withAudience(Environment.audience)
            .withIssuer(Environment.issuer)
            .build()
    }

    val JWTPrincipal.getIdFromBearerToken: Int
        get() = payload.getClaim("id").asInt() ?: -1
}