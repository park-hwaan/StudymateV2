package com.example.studymatetwo.repositoryImpl

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.repository.BoardRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BoardRepositoryImpl @Inject constructor(private val apiService: ApiService) : BoardRepository {

    @Singleton
    override suspend fun getBoardList(userToken: String) : List<BoardDto>{
        return try{
            val response = apiService.getBoardList(userToken)
            response
        }catch (e: HttpException) {
            emptyList()
        } catch (e: IOException) {
            emptyList()
        } catch (e: IllegalStateException) {
            emptyList()
        }
    }

    @Singleton
    override suspend fun getBoardContent(userToken: String, boardId: String) : ApiResponse<BoardDto>{
        return try{
            val response = apiService.getBoardContent(userToken,boardId)
            ApiResponse.Success(response)
        }catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IllegalStateException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        }
    }

    @Singleton
    override suspend fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto): ApiResponse<String>{
        return try{
            val response = apiService.postBoardComment(userToken,boardId, commentContentDto)
            ApiResponse.Success(response)
        }catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IllegalStateException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        }
    }

    @Singleton
    override suspend fun getCommentList(userToken: String, boardId: String) : List<CommentDto>{
        return try{
            val response = apiService.getCommentList(userToken, boardId)
            response
        }catch (e: HttpException) {
            emptyList()
        } catch (e: IOException) {
            emptyList()
        } catch (e: IllegalStateException) {
            emptyList()
        }
    }

    @Singleton
    override suspend fun getBoardSearch(userToken: String, keyword: String) : List<BoardDto>{
        return try{
            val response = apiService.getBoardSearch(userToken, keyword)
            response
        }catch (e: HttpException) {
            emptyList()
        } catch (e: IOException) {
            emptyList()
        } catch (e: IllegalStateException) {
            emptyList()
        }
    }
}