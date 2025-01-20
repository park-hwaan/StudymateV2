package com.example.studymatetwo.repositoryImpl

import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.repository.BoardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardRepositoryImpl @Inject constructor(private val apiService: ApiService) : BoardRepository {

    @Singleton
    override suspend fun getBoardList(userToken: String) : Result<List<BoardDto>> = apiService.getBoardList(userToken)

    @Singleton
    override suspend fun getBoardContent(userToken: String, boardId: String): Result<BoardDto> = apiService.getBoardContent(userToken,boardId)

    @Singleton
    override suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): Result<String> = apiService.postBoardComment(userToken,boardId,commentContentDto)

    @Singleton
    override suspend fun getCommentList(userToken: String, boardId: String) : Result<List<CommentDto>> = apiService.getCommentList(userToken, boardId)

    @Singleton
    override suspend fun getBoardSearch(userToken: String, keyword: String) : Result<List<BoardDto>> = apiService.getBoardSearch(userToken,keyword)

}