package com.example.studymatetwo.repositoryImpl

import androidx.lifecycle.LiveData
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.repository.BoardRepository
import com.example.studymatetwo.room.dao.BoardDao
import com.example.studymatetwo.room.entity.BoardEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardRepositoryImpl @Inject constructor(
    private val apiService: ApiService, private val boardDao: BoardDao
    ) : BoardRepository {

    fun getLocalBoardList(): LiveData<List<BoardEntity>> = boardDao.getAllData()

    @Singleton
    override suspend fun getBoardList(userToken: String): Result<List<com.example.studymatetwo.dto.BoardDto>> {
        return apiService.getBoardList(userToken).onSuccess { boardList ->
            val entities = boardList.map { it.toEntity() }
            withContext(Dispatchers.IO) { boardDao.insert(entities) }
        }
    }

    @Singleton
    override suspend fun getBoardContent(userToken: String, boardId: String): Result<com.example.studymatetwo.dto.BoardDto> = apiService.getBoardContent(userToken,boardId)

    @Singleton
    override suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): Result<String> = apiService.postBoardComment(userToken,boardId,commentContentDto)

    @Singleton
    override suspend fun getCommentList(userToken: String, boardId: String) : Result<List<CommentDto>> = apiService.getCommentList(userToken, boardId)

    @Singleton
    override suspend fun getBoardSearch(userToken: String, keyword: String) : Result<List<com.example.studymatetwo.dto.BoardDto>> = apiService.getBoardSearch(userToken,keyword)

}

fun com.example.studymatetwo.dto.BoardDto.toEntity(): BoardEntity {
    return BoardEntity(
        post_id = this.post_id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        nickname = this.nickname,
        category = this.category,
        interests = this.interests,
        recruitmentStatus = this.recruitmentStatus,
        likeCount = this.likeCount,
        commentCount = this.commentCount
    )
}
