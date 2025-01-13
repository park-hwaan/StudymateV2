package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto

interface BoardRepository {
    suspend fun getBoardList(userToken: String): List<BoardDto>

    suspend fun getBoardContent(userToken: String, boardId: String) : ApiResponse<BoardDto>
    suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): ApiResponse<String>

    suspend fun getCommentList(userToken: String, boardId: String) : List<CommentDto>

    suspend fun getBoardSearch(userToken: String, keyword: String) : List<BoardDto>
}