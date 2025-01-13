package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.MyInfoDto
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.dto.SignUpDto

interface SignRepository {
    suspend fun getMyAccountInfo(userToken: String) : ApiResponse<MyInfoDto>

    suspend fun postSignIn(signInDto: SignInDto): ApiResponse<SignInResponseDto>

    suspend fun postSignUp(signUpDto: SignUpDto) : ApiResponse<String>

}