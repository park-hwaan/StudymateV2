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
    ) : Result<SignInResponseDto>

    //회원가입
    @POST("api/signIn")
    suspend fun postSignUp(
        @Body signUpDto : SignUpDto
    ) : Result<String>

    //멘토에게 할 질문 post
    @POST("api/question")
    suspend fun postQuestion(
        @Header("Authorization") authorization: String,
        @Body quesInfo: MenteeQuestionDto
    ): Result<MenteeQuestionResponseDto>

    //질문에 대한 멘토리스트 불러오기
    @GET("api/matching/{questionId}")
    suspend fun getMentorList(
        @Header("Authorization") authorization: String,
        @Path("questionId") questionId : String
    ): Result<MentorListDto>

    //자기 계정정보 불러오가
    @GET("api/user")
    suspend fun getUserInfo(
        @Header("Authorization") authorization: String
    ): Result<MyInfoDto>

    @POST("api/chat/room")
    suspend fun postChatroom(
        @Header("Authorization") authorization: String,
        @Query("name") name: String
    ) : Result<String>

    @GET("api/chat/rooms")
    suspend fun getChatRoomList(
        @Header("Authorization") authorization: String
    ) : List<ChatRoomDto>

    @GET("api/posts")
    suspend fun getBoardList(
        @Header("Authorization") authorization: String
    ) : Result<List<BoardDto>>

    @GET("api/posts/{post_id}")
    suspend fun getBoardContent(
        @Header("Authorization") authorization: String,
        @Path("post_id") postId : String
    ) : Result<BoardDto>

    @POST("api/posts/{post_id}/comments")
    suspend fun postBoardComment(
        @Header("Authorization") authorization: String,
        @Path("post_id") postId : String,
        @Body commentContentDto : CommentContentDto
    ) : Result<String>

    @GET("api/posts/{post_id}/comments")
    suspend fun getCommentList(
        @Header("Authorization") authorization: String,
        @Path("post_id") postId : String
    ) : Result<List<CommentDto>>

    @GET("api/posts/search")
    suspend fun getBoardSearch(
        @Header("Authorization") authorization: String,
        @Query("keyword") keyword: String
    ) : Result<List<BoardDto>>
}