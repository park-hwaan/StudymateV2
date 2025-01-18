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
import com.example.studymatetwo.error.AppError
import com.example.studymatetwo.error.HttpError
import com.example.studymatetwo.repository.SignRepository
import com.example.studymatetwo.repositoryImpl.SignRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val repository: SignRepositoryImpl) : ViewModel()  {

    private val _myInfoData = MutableLiveData<MyInfoDto>()
    val myInfoData: LiveData<MyInfoDto> = _myInfoData

    private val _signInResult = MutableLiveData<SignInResponseDto>()
    val signInResult: LiveData<SignInResponseDto> = _signInResult

    private val _signUpResult = MutableLiveData<String>()
    val signUpResult: LiveData<String> = _signUpResult

    private var _mutableErrorState = MutableLiveData<String>()
    val errorState : LiveData<String> get() = _mutableErrorState

    private val _signUpData = MutableLiveData<SignUpDto>().apply { value = SignUpDto() }
    val signUpData: LiveData<SignUpDto> get() = _signUpData

    private val _cursor = MutableLiveData(1)
    val cursor: LiveData<Int> get() = _cursor

    fun getMyInfo(userToken: String) = viewModelScope.launch {
        repository.getMyAccountInfo(userToken)
            .onSuccess {
                _myInfoData.postValue(it)
            }
            .onFailure {
                _mutableErrorState.postValue(it.handleError())
            }
    }

    fun postSignIn(signInModel: SignInDto) = viewModelScope.launch(Dispatchers.IO) {
        if (!validateSignInModel(signInModel)) {
            withContext(Dispatchers.Main){
                _mutableErrorState.postValue("입력값을 확인해주세요")
            }
        }else{
            repository.postSignIn(signInModel)
                .onSuccess {
                    _signInResult.postValue(it)
                }
                .onFailure {
                    _mutableErrorState.postValue(it.handleError())
                }
        }
    }

    fun postSignUp(signUpDto: SignUpDto) = viewModelScope.launch {
        repository.postSignUp(signUpDto)
            .onSuccess {
                _signUpResult.postValue(it)
            }
            .onFailure {
                _mutableErrorState.postValue(it.handleError())
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

    private fun Throwable.handleError(): String {
        val errorMessages = mapOf(
            AppError.UnexpectedError::class to "예상치 못한 에러입니다..",
            AppError.NetworkError::class to "네트워크 에러가 떴어요..",
            HttpError.BadRequestError::class to "bad request",
            HttpError.UnauthorizedError::class to "unauthorized",
            HttpError.ForbiddenError::class to "Forbidden",
            HttpError.NotFoundError::class to "Not Found",
            HttpError.InternalServerError::class to "Internal Server Error"
        )
        return errorMessages[this::class] ?: "알 수 없는 에러"
    }
}