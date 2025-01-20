package com.example.studymatetwo.repository

import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import javax.inject.Singleton

@Singleton
interface BoardRepository {
    suspend fun getBoardList(userToken: String): Result<List<BoardDto>>
    suspend fun getBoardContent(userToken: String, boardId: String) : Result<BoardDto>
    suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): Result<String>
    suspend fun getCommentList(userToken: String, boardId: String) : Result<List<CommentDto>>
    suspend fun getBoardSearch(userToken: String, keyword: String) : Result<List<BoardDto>>
}