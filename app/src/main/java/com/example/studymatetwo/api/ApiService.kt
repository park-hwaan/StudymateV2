package com.example.studymatetwo.api

import com.example.studymatetwo.dto.SignInDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //로그인
    @POST("api/login")
    suspend fun postSignIn(
        @Body signInDto: SignInDto
    )
}