package com.example.studymatetwo.repository

import android.content.Context
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.MyInfoDto
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.dto.SignUpDto
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SignRepository @Inject constructor(private val apiService: ApiService, @ApplicationContext private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    @Singleton
    suspend fun getMyAccountInfo(userToken: String) : ApiResponse<MyInfoDto>{
        return try {
            val response = apiService.getUserInfo(userToken)
            ApiResponse.Success(response)
        }catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "네트워크 오류 발생")
        } catch (e: IllegalStateException) {
            ApiResponse.Error(e.message ?: "알 수 없는 오류 발생")
        }
    }

    @Singleton
    //로그인 서비스
    suspend fun postSignIn(signInDto: SignInDto): ApiResponse<SignInResponseDto> {
        return try {
            val response = apiService.postSignIn(signInDto)
            val editor = sharedPreferences.edit()
            editor.putString("userToken", response.accessToken)
            editor.putString("refreshToken", response.refreshToken)
            editor.apply()
            ApiResponse.Success(response)
        } catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "네트워크 오류 발생")
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "알 수 없는 오류 발생")
        }
    }

    @Singleton
    //회원가입 서비스
    suspend fun postSignUp(signUpDto: SignUpDto) : ApiResponse<String>{
        return try{
            val response = apiService.postSignUp(signUpDto)
            ApiResponse.Success(response)
        }catch (e: HttpException) {
            ApiResponse.Error(e.message ?: "HTTP 오류 발생")
        } catch (e: IOException) {
            ApiResponse.Error(e.message ?: "네트워크 오류 발생")
        } catch (e: IllegalStateException) {
            ApiResponse.Error(e.message ?: "알 수 없는 오류 발생")
        }
    }

}