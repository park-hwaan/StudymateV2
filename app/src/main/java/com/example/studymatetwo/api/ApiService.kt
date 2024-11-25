package com.example.studymatetwo.api

import com.example.studymatetwo.dto.*
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

    //멘토에게 할 질문 post
    @POST("api/question")
    suspend fun postQuestion(
        @Header("Authorization") authorization: String,
        @Body quesInfo: MenteeQuestionDto
    ): String

    //자기 계정정보 불러오가
    @POST("api/question")
    suspend fun getUserInfo(
        @Header("Authorization") authorization: String
    ): MyInfoDto


}