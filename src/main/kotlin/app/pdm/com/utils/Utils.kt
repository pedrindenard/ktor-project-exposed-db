package app.pdm.com.utils

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
}