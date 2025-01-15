package com.example.studymatetwo.repositoryImpl

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.dto.MenteeQuestionResponseDto
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.dto.MentorListDto
import com.example.studymatetwo.repository.MentorSearchRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MentorSearchRepositoryImpl @Inject constructor(private val apiService: ApiService) : MentorSearchRepository {

    @Singleton
    override suspend fun postQuestion(userToken: String, menteeQuestionDto: MenteeQuestionDto) : Result<MenteeQuestionResponseDto> = apiService.postQuestion(userToken, menteeQuestionDto)

    @Singleton
    override suspend fun getMentorList(userToken: String, questionId: String): Result<MentorListDto> = apiService.getMentorList(userToken, questionId)

    @Singleton
    override suspend fun postChatRoom(userToken: String, name: String): Result<String> = apiService.postChatroom(userToken, name)
}