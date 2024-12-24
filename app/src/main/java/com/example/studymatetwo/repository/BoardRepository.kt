package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.BoardDto
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
}