package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.MyInfoDto
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.dto.SignUpDto

interface SignRepository {
    suspend fun getMyAccountInfo(userToken: String) : Result<MyInfoDto>

    suspend fun postSignIn(signInDto: SignInDto): Result<SignInResponseDto>

    suspend fun postSignUp(signUpDto: SignUpDto) : Result<String>

}