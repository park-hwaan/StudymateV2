package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.api.RetrofitWork
import com.example.studymatetwo.dto.SignInDto

class SignRepository : ApiService {
    private val retrofitInstance = RetrofitWork.getInstance().create(ApiService::class.java)

    override suspend fun postSignIn(signInDto: SignInDto) {
        retrofitInstance.postSignIn(signInDto)
    }
}