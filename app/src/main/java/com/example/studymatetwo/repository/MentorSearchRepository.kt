package com.example.studymatetwo.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.MenteeQuestionDto
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
    suspend fun postQuestion(userToken: String, menteeQuestionDto: MenteeQuestionDto) : ApiResponse<String>{
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
}