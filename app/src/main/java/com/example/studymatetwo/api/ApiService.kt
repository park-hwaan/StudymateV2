package com.example.studymatetwo.api

import com.example.studymatetwo.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
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
    ): MenteeQuestionResponseDto

    //질문에 대한 멘토리스트 불러오기
    @GET("api/matching/{questionId}")
    suspend fun getMentorList(
        @Header("Authorization") authorization: String,
        @Path("questionId") questionId : String
    ): MentorListDto

    //자기 계정정보 불러오가
    @GET("api/user")
    suspend fun getUserInfo(
        @Header("Authorization") authorization: String
    ): MyInfoDto

    @POST("api/chat/room")
    suspend fun postChatroom(
        @Header("Authorization") authorization: String,
        @Query("name") name: String
    ) : String

}