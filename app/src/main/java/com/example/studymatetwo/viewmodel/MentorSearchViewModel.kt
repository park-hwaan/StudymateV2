package com.example.studymatetwo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.MenteeQuestionDto
import com.example.studymatetwo.dto.SignInDto
import com.example.studymatetwo.dto.SignUpDto
import com.example.studymatetwo.repository.MentorSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MentorSearchViewModel @Inject constructor(private val repository: MentorSearchRepository) : ViewModel() {

    private val _postMenteeQuestionResult = MutableLiveData<ApiResponse<String>>()
    val postMenteeQuestionResult: LiveData<ApiResponse<String>> = _postMenteeQuestionResult

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

    private fun validateQuestionModel(model: MenteeQuestionDto): Boolean {
        return model.title.isNotBlank() &&
                model.content.isNotBlank() &&
                model.specificField.isNotBlank()
    }
}