package app.pdm.com.utils

import io.ktor.server.config.*

object Environment {

    @JvmStatic
    val environment = ApplicationConfig(configPath = null)

    /* ----- JWT AUTHENTICATION ----- */
    val secret = environment.propertyOrNull(path = "ktor.authentication.secret")!!.getString()
    val audience = environment.propertyOrNull(path = "ktor.authentication.audience")!!.getString()
    val issuer = environment.propertyOrNull(path = "ktor.authentication.issuer")!!.getString()
    val realm = environment.propertyOrNull(path = "ktor.authentication.realm")!!.getString()

    /* ----- DATABASE CONNECTION ----- */
    val driverClassName = environment.propertyOrNull(path = "ktor.database.driverClassName")!!.getString()
    val jdbcURL = environment.propertyOrNull(path = "ktor.database.jdbcURL")!!.getString()
}