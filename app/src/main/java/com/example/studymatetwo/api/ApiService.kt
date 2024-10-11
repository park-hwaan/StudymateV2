package com.example.studymatetwo.api

import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.dto.SignUpDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //로그인
    @POST("api/login")
    suspend fun postSignIn(
        @Body signInDto: SignInDto
    ) : SignInResponseDto

    //회원가입
    @POST("api/signIn")
    suspend fun postSignUp(
        @Body signUpDto : SignUpDto
    )
}