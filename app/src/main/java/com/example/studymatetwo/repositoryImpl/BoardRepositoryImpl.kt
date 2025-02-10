package com.example.studymatetwo.repositoryImpl

import androidx.lifecycle.LiveData
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.BoardDto
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
    private val apiService: ApiService,
    private val boardDao: BoardDao
) : BoardRepository {

    override fun getLocalBoardList(): LiveData<List<BoardEntity>> = boardDao.getAllData()

    override suspend fun getBoardList(userToken: String): Result<List<BoardDto>> {
        return apiService.getBoardList(userToken).onSuccess { boardList ->
            val entities = boardList.map { it.toEntity() }
            withContext(Dispatchers.IO) { boardDao.insert(entities) }
        }
    }

    override suspend fun getBoardContent(userToken: String, boardId: String): Result<BoardDto> =
        apiService.getBoardContent(userToken, boardId)

    override suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): Result<String> =
        apiService.postBoardComment(userToken, boardId, commentContentDto)

    override suspend fun getCommentList(userToken: String, boardId: String): Result<List<CommentDto>> =
        apiService.getCommentList(userToken, boardId)

    override suspend fun getBoardSearch(userToken: String, keyword: String): Result<List<BoardDto>> =
        apiService.getBoardSearch(userToken, keyword)
}

// 확장 함수: BoardDto를 BoardEntity로 변환
fun BoardDto.toEntity(): BoardEntity {
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
