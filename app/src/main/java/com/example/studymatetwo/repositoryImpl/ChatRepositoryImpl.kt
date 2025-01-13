package com.example.studymatetwo.repositoryImpl

import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.ChatRoomDto
import com.example.studymatetwo.repository.ChatRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatRepositoryImpl @Inject constructor(private val apiService: ApiService) : ChatRepository {

    @Singleton
    override suspend fun getChatRoom(userToken: String) : List<ChatRoomDto>{
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