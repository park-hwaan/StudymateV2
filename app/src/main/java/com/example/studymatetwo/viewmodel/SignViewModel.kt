package com.example.studymatetwo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.repository.SignRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignViewModel : ViewModel() {
    private val _signInResult = MutableLiveData<ApiResponse<SignInResponseDto>>()
    val signInResult: LiveData<ApiResponse<SignInResponseDto>> = _signInResult

    private val repository = SignRepository()

    // Sign-In using Retrofit
    fun postSignIn(signInModel: SignInDto) {
        if (!validateSignInModel(signInModel)) {
            _signInResult.value = ApiResponse.Error("입력값을 확인해주세요")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _signInResult.postValue(ApiResponse.Loading())

            val response = repository.postSignIn(signInModel)
            _signInResult.postValue(response)
        }
    }

    // 로그인 유효성 검사 로직
    private fun validateSignInModel(model: SignInDto): Boolean {
        return model.email.isNotBlank() &&
                model.password.isNotBlank()
    }
}