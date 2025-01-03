package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentDto
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BoardRepository @Inject constructor(private val apiService: ApiService) {

    @Singleton
    suspend fun getBoardList(userToken: String) : List<BoardDto>{
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
    suspend fun getBoardContent(userToken: String, boardId: String) : ApiResponse<BoardDto>{
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
    suspend fun postBoardComment(userToken: String, boardId: String, commentDto: CommentDto): ApiResponse<String>{
        return try{
            val response = apiService.postBoardComment(userToken,boardId, commentDto)
            ApiResponse.Success(response)
        }catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IllegalStateException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        }
    }
}