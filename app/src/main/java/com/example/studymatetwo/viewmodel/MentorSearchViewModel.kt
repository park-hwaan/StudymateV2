package com.example.studymatetwo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.*
import com.example.studymatetwo.repository.MentorSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MentorSearchViewModel @Inject constructor(private val repository: MentorSearchRepository) : ViewModel() {

    private val _progressBarValue = MutableLiveData<Int>(1)
    val progressBarValue: LiveData<Int> get() = _progressBarValue

    private val _postChatRoomResult = MutableLiveData<ApiResponse<String>>()
    val postChatRoomResult: LiveData<ApiResponse<String>> get() = _postChatRoomResult

    private val _postMenteeQuestionResult = MutableLiveData<ApiResponse<MenteeQuestionResponseDto>>()
    val postMenteeQuestionResult: LiveData<ApiResponse<MenteeQuestionResponseDto>> get() = _postMenteeQuestionResult

    private val _questionData = MutableLiveData<MenteeQuestionDto>().apply { value = MenteeQuestionDto() }
    val questionData: LiveData<MenteeQuestionDto> get() = _questionData

    private var _mutableMentorList = MutableLiveData<List<MentorDto>>(emptyList())
    val mentorList: LiveData<List<MentorDto>> get() = _mutableMentorList

    private val _nextBtnText = MutableLiveData<String>().apply { value = "다음" }
    val nextBtnText: LiveData<String> = _nextBtnText

    private val _cursor = MutableLiveData<Int>(1)
    val cursor: LiveData<Int> get() = _cursor

    private val lastCursor = 3

    fun postMenteeQuestion(userToken: String, menteeQuestionDto: MenteeQuestionDto){
       if(!validateQuestionModel(menteeQuestionDto)){
           _postMenteeQuestionResult.value = ApiResponse.Error("입력값을 확인해주세요")
       }else{
           viewModelScope.launch(Dispatchers.IO) {
               val response = repository.postQuestion(userToken, menteeQuestionDto)
               _postMenteeQuestionResult.postValue(response)
           }
       }
    }

    fun getMentorList(userToken: String, questionId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMentorList(userToken, questionId)
            _mutableMentorList.postValue(response)
        }
    }

    fun postChatRoom(userToken : String, name: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postChatRoom(userToken,name)
            _postChatRoomResult.postValue(response)
        }
    }

    private fun validateQuestionModel(model: MenteeQuestionDto): Boolean {
        return model.title.isNotBlank() &&
                model.content.isNotBlank() &&
                model.specificField.isNotBlank()
    }

    private fun updateProgressBarValue(newValue: Int) {
        _progressBarValue.value = newValue
    }

    fun nextCursor() {
        val currentCursor = _cursor.value ?: 1

        if (currentCursor + 1 == lastCursor) {
            _nextBtnText.value = "멘토검색"
        } else {
            _nextBtnText.value = "다음"
        }
        _cursor.value = currentCursor + 1
        updateProgressBarValue(currentCursor + 1)
    }

    fun previousCursor() {
        val currentCursor = (_cursor.value ?: 1) - 1
        _cursor.value = currentCursor
        updateProgressBarValue(currentCursor)

        if (currentCursor != lastCursor) {
            _nextBtnText.value = "다음"
        } else {
            _nextBtnText.value = "완료"
        }
    }


    fun updateQuestionData(newData: MenteeQuestionDto) {
        _questionData.value = newData
    }
}