package com.example.studymatetwo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(private val repository: BoardRepository) : ViewModel() {
    private var _mutableBoardList = MutableLiveData<List<BoardDto>>(emptyList())
    val boardList: LiveData<List<BoardDto>> get() = _mutableBoardList

    private var _mutableBoardContent = MutableLiveData<ApiResponse<BoardDto>>()
    val boardContent: LiveData<ApiResponse<BoardDto>> get() = _mutableBoardContent

    fun getBoardList(userToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBoardList(userToken)
            _mutableBoardList.postValue(response)
        }
    }

    fun getBoardContent(userToken: String, boardId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBoardContent(userToken, boardId)
            _mutableBoardContent.postValue(response)
        }
    }
}