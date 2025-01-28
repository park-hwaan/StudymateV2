package com.example.studymatetwo.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "board_table")
data class BoardEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "post_id")
    var post_id: String,
    @ColumnInfo(name= "title")
    var title: String,
    @ColumnInfo(name= "content")
    var content: String,
    @ColumnInfo(name= "createdAt")
    var createdAt : String,
    @ColumnInfo(name= "nickname")
    var nickname : String,
    @ColumnInfo(name= "category")
    var category: String,
    @ColumnInfo(name= "interests")
    var interests: String,
    @ColumnInfo(name= "recruitmentStatus")
    var recruitmentStatus : String,
    @ColumnInfo(name= "likeCount")
    var likeCount : Int,
    @ColumnInfo(name= "commentCount")
    var commentCount : Int
)