package com.example.studymatetwo.repository

import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.api.RetrofitWork
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class SignRepository {
    private val retrofitInstance = RetrofitWork.getInstance().create(ApiService::class.java)
    suspend fun postSignIn(signInDto: SignInDto): ApiResponse<SignInResponseDto> {
        return try {
            val response = retrofitInstance.postSignIn(signInDto)
            ApiResponse.Success(response)
        } catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "네트워크 오류 발생")
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "알 수 없는 오류 발생")
        }
    }


}