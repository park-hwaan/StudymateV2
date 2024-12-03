package com.example.studymatetwo.dto

data class MenteeQuestionResponseDto (
    val id : String,
    val writer : String,
    val title: String,
    val content : String,
    val specificField: String,
    val interests: String,
    val isSolved: Boolean,
    val createAt: String
        )