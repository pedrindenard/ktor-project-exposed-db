package app.pdm.com.utils

import io.ktor.http.*

sealed class BaseResponse<T> {

    data class Success<T>(val data: T, val code: HttpStatusCode) : BaseResponse<T>()

    data class Failure<T>(val throwable: T, val code: HttpStatusCode) : BaseResponse<T>()

    data class Error<T>(val message: T, val code: HttpStatusCode) : BaseResponse<T>()
}