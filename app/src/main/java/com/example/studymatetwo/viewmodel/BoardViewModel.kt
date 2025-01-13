package com.example.studymatetwo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.repository.BoardRepository
import com.example.studymatetwo.repositoryImpl.BoardRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(private val repository: BoardRepositoryImpl) : ViewModel() {
    private var _mutableBoardList = MutableLiveData<List<BoardDto>>(emptyList())
    val boardList: LiveData<List<BoardDto>> get() = _mutableBoardList

    private var _mutableBoardSearchList = MutableLiveData<List<BoardDto>>(emptyList())
    val boardSearchList: LiveData<List<BoardDto>> get() = _mutableBoardSearchList

    private var _mutableBoardContent = MutableLiveData<ApiResponse<BoardDto>>()
    val boardContent: LiveData<ApiResponse<BoardDto>> get() = _mutableBoardContent

    private var _mutableCommentList = MutableLiveData<List<CommentDto>>(emptyList())
    val commentList: LiveData<List<CommentDto>> get() = _mutableCommentList

    private var _mutableBoardComment = MutableLiveData<ApiResponse<String>>()
    val boardComment: LiveData<ApiResponse<String>> get() = _mutableBoardComment

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

    fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postBoardComment(userToken, boardId, commentContentDto)
            _mutableBoardComment.postValue(response)
        }
    }

    fun getCommentList(userToken: String, boardId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCommentList(userToken, boardId)
            _mutableCommentList.postValue(response)
        }
    }

    fun getBoardSearch(userToken: String, keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBoardSearch(userToken, keyword)
            _mutableBoardSearchList.postValue(response)
        }
    }

}