package com.example.studymatetwo.repositoryImpl

import android.content.Context
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.api.ApiService
import com.example.studymatetwo.dto.MyInfoDto
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.dto.SignUpDto
import com.example.studymatetwo.repository.SignRepository
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
class SignRepositoryImpl @Inject constructor(private val apiService: ApiService, @ApplicationContext private val context: Context) : SignRepository{
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    @Singleton
    override suspend fun getMyAccountInfo(userToken: String) : Result<MyInfoDto> = apiService.getUserInfo(userToken)

    @Singleton
    //로그인 서비스
    override suspend fun postSignIn(signInDto: SignInDto): Result<SignInResponseDto> = apiService.postSignIn(signInDto)

    @Singleton
    //회원가입 서비스
    override suspend fun postSignUp(signUpDto: SignUpDto): Result<String> = apiService.postSignUp(signUpDto)

}