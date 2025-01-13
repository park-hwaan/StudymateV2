package com.example.studymatetwo.error

import com.google.gson.Gson

data class ErrorResponse(
    val status: String,
    val title: String,
    val type: String,
    val detail: String,
    val message: String
)

fun fromJsonToErrorResponse(json: String): ErrorResponse {
    return Gson().fromJson(json, ErrorResponse::class.java)
}