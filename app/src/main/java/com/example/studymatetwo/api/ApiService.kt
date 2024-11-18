package com.example.studymatetwo.api

import com.example.studymatetwo.dto.QuestionDto
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.dto.SignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
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
    ) : String

    //질문 post
    @POST("/api/question")
    suspend fun postQuestion(
        @Header("Authorization") authorization: String,
        @Body quesInfo: QuestionDto
    ): String
}