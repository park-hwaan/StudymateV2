package com.example.studymatetwo.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.dto.MenteeQuestionResponseDto
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.dto.MentorListDto
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MentorSearchRepository @Inject constructor(private val apiService: ApiService) {

    @Singleton
    suspend fun postQuestion(userToken: String, menteeQuestionDto: MenteeQuestionDto) : ApiResponse<MenteeQuestionResponseDto>{
        return try {
            val response = apiService.postQuestion(userToken, menteeQuestionDto)
            ApiResponse.Success(response)
        }catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "네트워크 오류 발생")
        } catch (e: IllegalStateException) {
            ApiResponse.Error(e.message ?: "알 수 없는 오류 발생")
        }
    }

    @Singleton
    suspend fun getMentorList(userToken: String, questionId: String) : List<MentorDto>{
        return try{
            val response = apiService.getMentorList(userToken, questionId)
            response.memberList
        }catch (e: HttpException) {
           emptyList()
        } catch (e: IOException) {
            emptyList()
        } catch (e: IllegalStateException) {
            emptyList()
        }
    }

    @Singleton
    suspend fun postChatRoom(userToken: String, name: String) : ApiResponse<String> {
        return try {
            val response = apiService.postChatroom(userToken, name)
            ApiResponse.Success(response)
        } catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "네트워크 오류 발생")
        } catch (e: IllegalStateException) {
            ApiResponse.Error(e.message ?: "알 수 없는 오류 발생")
        }
    }
}