package com.example.studymatetwo.repository

import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.dto.MenteeQuestionResponseDto
import com.example.studymatetwo.dto.MentorListDto
import javax.inject.Singleton

@Singleton
interface MentorSearchRepository {
    suspend fun postQuestion(userToken: String, menteeQuestionDto: MenteeQuestionDto) : Result<MenteeQuestionResponseDto>

    suspend fun getMentorList(userToken: String, questionId: String) : Result<MentorListDto>

    suspend fun postChatRoom(userToken: String, name: String) : Result<String>

}