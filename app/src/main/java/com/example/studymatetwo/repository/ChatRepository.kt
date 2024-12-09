package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.ChatRoomDto
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.dto.MenteeQuestionResponseDto
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatRepository @Inject constructor(private val apiService: ApiService) {

    @Singleton
    suspend fun getChatRoom(userToken: String) : List<ChatRoomDto>{
        return try {
            val response = apiService.getChatRoomList(userToken)
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