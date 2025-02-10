package com.example.studymatetwo.util

import com.example.studymatetwo.error.AppError
import com.example.studymatetwo.error.HttpError


fun Throwable.handleError(): String {
    val errorMessages = mapOf(
        AppError.UnexpectedError::class to "예상치 못한 에러입니다..",
        AppError.NetworkError::class to "네트워크 에러가 떴어요..",
        HttpError.BadRequestError::class to "bad request",
        HttpError.UnauthorizedError::class to "unauthorized",
        HttpError.ForbiddenError::class to "Forbidden",
        HttpError.NotFoundError::class to "Not Found",
        HttpError.InternalServerError::class to "Internal Server Error"
    )
    return errorMessages[this::class] ?: "알 수 없는 에러"
}