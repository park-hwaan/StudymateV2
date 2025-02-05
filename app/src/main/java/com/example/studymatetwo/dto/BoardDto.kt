package com.example.studymatetwo.dto

data class BoardDto (
    var post_id: Int,
    var title: String,
    var content: String,
    var createdAt : String,
    var nickname : String,
    var category: String,
    var interests: String,
    var recruitmentStatus : String,
    var likeCount : Int,
    var commentCount : Int
        )