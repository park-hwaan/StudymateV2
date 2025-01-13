package com.example.studymatetwo.error

sealed class HttpError: AppError(){
    data class BadRequestError(override val message: String) : HttpError() //400 Error

    data class UnauthorizedError(override val message: String) : HttpError() //401 Error

    data class ForbiddenError(override val message: String) : HttpError() //403 Error

    data class NotFoundError(override val message: String) : HttpError() //404 Error

    data class InternalServerError(override val message: String) : HttpError() //500 Error
}

fun getErrorByStatusCode(statusCode: Int, message: String): AppError {
    return when (statusCode) {
        400 -> HttpError.BadRequestError(message = message)
        401 -> HttpError.UnauthorizedError(message = message)
        403 -> HttpError.ForbiddenError(message = message)
        404 -> HttpError.NotFoundError(message = message)
        500 -> HttpError.InternalServerError(message = message)
        else -> AppError.UnexpectedError
    }
}