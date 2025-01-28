package com.example.studymatetwo.repositoryImpl

import androidx.lifecycle.LiveData
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.repository.BoardRepository
import com.example.studymatetwo.room.dao.BoardDao
import com.example.studymatetwo.room.entity.BoardEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardRepositoryImpl @Inject constructor(
    private val apiService: ApiService, private val boardDao: BoardDao
    ) : BoardRepository {

    fun getLocalBoardList(): LiveData<List<BoardEntity>> = boardDao.getAllData()

    @Singleton
    override suspend fun getBoardList(userToken: String): Result<List<BoardDto>> {
        return try {
            val result = apiService.getBoardList(userToken)

            result.onSuccess { boardDtoList ->
                saveBoardsToLocal(boardDtoList)
            }
            result
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @Singleton
    override suspend fun getBoardContent(userToken: String, boardId: String): Result<BoardDto> = apiService.getBoardContent(userToken,boardId)

    @Singleton
    override suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): Result<String> = apiService.postBoardComment(userToken,boardId,commentContentDto)

    @Singleton
    override suspend fun getCommentList(userToken: String, boardId: String) : Result<List<CommentDto>> = apiService.getCommentList(userToken, boardId)

    @Singleton
    override suspend fun getBoardSearch(userToken: String, keyword: String) : Result<List<BoardDto>> = apiService.getBoardSearch(userToken,keyword)

    private fun saveBoardsToLocal(boardDtoList: List<BoardDto>) {
        val boardEntities = boardDtoList.map { dto ->
            BoardEntity(
                post_id = dto.post_id, // DTO와 매핑
                title = dto.title,
                content = dto.content,
                createdAt = dto.createdAt,
                nickname = dto.nickname,
                category = dto.category,
                interests = dto.interests,
                recruitmentStatus = dto.recruitmentStatus,
                likeCount = dto.likeCount,
                commentCount = dto.commentCount
            )
        }
        boardDao.insert(boardEntities) // Room에 저장
    }


}