package com.example.studymatetwo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.dto.*
import com.example.studymatetwo.error.AppError
import com.example.studymatetwo.error.HttpError
import com.example.studymatetwo.repositoryImpl.MentorSearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MentorSearchViewModel @Inject constructor(private val repository: MentorSearchRepositoryImpl) : ViewModel() {
    private companion object { const val LAST_CURSOR = 3 }

    private val _progressBarValue = MutableLiveData(1)
    val progressBarValue: LiveData<Int> get() = _progressBarValue

    private val _postChatRoomResult = MutableLiveData<String>()
    val postChatRoomResult: LiveData<String> get() = _postChatRoomResult

    private val _postMenteeQuestionResult = MutableLiveData<MenteeQuestionResponseDto>()
    val postMenteeQuestionResult: LiveData<MenteeQuestionResponseDto> get() = _postMenteeQuestionResult

    private val _questionData = MutableLiveData<MenteeQuestionDto>().apply { value = MenteeQuestionDto() }
    val questionData: LiveData<MenteeQuestionDto> get() = _questionData

    private var _mutableMentorList = MutableLiveData<MentorListDto>()
    val mentorList: LiveData<MentorListDto> get() = _mutableMentorList

    private val _nextBtnText = MutableLiveData("다음")
    val nextBtnText: LiveData<String> = _nextBtnText

    private var _mutableErrorState = MutableLiveData<String>()
    val errorState : LiveData<String> get() = _mutableErrorState

    private val _cursor = MutableLiveData(1)
    val cursor: LiveData<Int> get() = _cursor

    private val lastCursor = LAST_CURSOR


    fun postMenteeQuestion(userToken: String, menteeQuestionDto: MenteeQuestionDto) = viewModelScope.launch(Dispatchers.IO){
        if(!menteeQuestionDto.isValid()){
            withContext(Dispatchers.Main){
                _mutableErrorState.value = "입력값을 확인해주세요"
            }
        }else{
            repository.postQuestion(userToken, menteeQuestionDto)
                .onSuccess {
                    _postMenteeQuestionResult.postValue(it)
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        _mutableErrorState.value = it.handleError()
                    }
                }
        }
    }

    fun getMentorList(userToken: String, questionId: String) = viewModelScope.launch(Dispatchers.Main){
        repository.getMentorList(userToken, questionId)
            .onSuccess {
                _mutableMentorList.postValue(it)
            }
            .onFailure {
                withContext(Dispatchers.Main) {
                    _mutableErrorState.value = it.handleError()
                }
            }
    }

    fun postChatRoom(userToken : String, name: String) = viewModelScope.launch(Dispatchers.IO){
        repository.postChatRoom(userToken,name)
            .onSuccess {
                _postChatRoomResult.postValue(it)
            }
            .onFailure {
                withContext(Dispatchers.Main) {
                    _mutableErrorState.value = it.handleError()
                }
            }
    }

    fun nextCursor() {
        val current = (_cursor.value ?: 1) + 1
        _cursor.value = current
        _progressBarValue.value = current
        _nextBtnText.value = if (current == lastCursor) "멘토검색" else "다음"
    }

    fun previousCursor() {
        val current = (_cursor.value ?: 1) - 1
        _cursor.value = current
        _progressBarValue.value = current
        _nextBtnText.value = if (current == lastCursor) "완료" else "다음"
    }

    fun updateQuestionData(newData: MenteeQuestionDto) {
        _questionData.value = newData
    }

    private fun Throwable.handleError(): String {
        val errorMessages = mapOf(
            AppError.NetworkError::class to "네트워크 에러가 떴어요..",
            HttpError.BadRequestError::class to "bad request",
            HttpError.UnauthorizedError::class to "unauthorized",
            HttpError.ForbiddenError::class to "Forbidden",
            HttpError.NotFoundError::class to "Not Found",
            HttpError.InternalServerError::class to "Internal Server Error"
        )
        return errorMessages[this::class] ?: "알 수 없는 에러"
    }

    private fun MenteeQuestionDto.isValid(): Boolean {
        return title.isNotBlank() && content.isNotBlank() && specificField.isNotBlank()
    }
}
