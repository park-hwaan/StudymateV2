package com.example.studymatetwo.repository

import androidx.lifecycle.LiveData
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.room.entity.BoardEntity
import javax.inject.Singleton

@Singleton
interface BoardRepository {

    // 로컬 DB의 데이터를 반환하는 함수도 인터페이스에 포함
    fun getLocalBoardList(): LiveData<List<BoardEntity>>

    suspend fun getBoardList(userToken: String): Result<List<BoardDto>>
    suspend fun getBoardContent(userToken: String, boardId: String): Result<BoardDto>
    suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): Result<String>
    suspend fun getCommentList(userToken: String, boardId: String): Result<List<CommentDto>>
    suspend fun getBoardSearch(userToken: String, keyword: String): Result<List<BoardDto>>
}
