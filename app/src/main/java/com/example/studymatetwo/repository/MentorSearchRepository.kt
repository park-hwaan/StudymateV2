package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.dto.MenteeQuestionResponseDto
import com.example.studymatetwo.dto.MentorDto

interface MentorSearchRepository {
    suspend fun postQuestion(userToken: String, menteeQuestionDto: MenteeQuestionDto) : ApiResponse<MenteeQuestionResponseDto>

    suspend fun getMentorList(userToken: String, questionId: String) : List<MentorDto>

    suspend fun postChatRoom(userToken: String, name: String) : ApiResponse<String>

}