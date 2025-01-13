package com.example.studymatetwo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.MyInfoDto
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignInResponseDto
import com.example.studymatetwo.dto.SignUpDto
import com.example.studymatetwo.repository.SignRepository
import com.example.studymatetwo.repositoryImpl.SignRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val repository: SignRepositoryImpl) : ViewModel()  {

    private val _myInfoData = MutableLiveData<ApiResponse<MyInfoDto>>()
    val myInfoData: LiveData<ApiResponse<MyInfoDto>> = _myInfoData

    private val _signInResult = MutableLiveData<ApiResponse<SignInResponseDto>>()
    val signInResult: LiveData<ApiResponse<SignInResponseDto>> = _signInResult

    private val _signUpResult = MutableLiveData<ApiResponse<String>>()
    val signUpResult: LiveData<ApiResponse<String>> = _signUpResult

    private val _signUpData = MutableLiveData<SignUpDto>().apply { value = SignUpDto() }
    val signUpData: LiveData<SignUpDto> get() = _signUpData

    private val _cursor = MutableLiveData<Int>(1)
    val cursor: LiveData<Int> get() = _cursor

    fun getMyInfo(userToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMyAccountInfo(userToken)
            _myInfoData.postValue(response)
        }
    }

    fun postSignIn(signInModel: SignInDto) {
        if (!validateSignInModel(signInModel)) {
            _signInResult.value = ApiResponse.Error("입력값을 확인해주세요")
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                _signInResult.postValue(ApiResponse.Loading())
                val response = repository.postSignIn(signInModel)
                _signInResult.postValue(response)
            }
        }
    }

    fun postSignUp(signUpDto: SignUpDto){
        viewModelScope.launch(Dispatchers.IO) {
            _signUpResult.postValue(ApiResponse.Loading())  // 로딩 상태 업데이트
            val response = repository.postSignUp(signUpDto)
            _signUpResult.postValue(response)
        }
    }

    private fun validateSignInModel(model: SignInDto): Boolean {
        return model.email.isNotBlank() && model.password.isNotBlank()
    }

    fun updateSignUpData(newData: SignUpDto) {
        _signUpData.value = newData
    }

    fun nextCursor() {
        _cursor.value = (_cursor.value ?: 1) + 1
    }

    fun previousCursor() {
        _cursor.value = (_cursor.value ?: 1) - 1
    }
}