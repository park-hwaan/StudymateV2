package com.example.studymatetwo.viewmodel

import android.view.animation.Transformation
import androidx.lifecycle.*
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.error.AppError
import com.example.studymatetwo.error.HttpError
import com.example.studymatetwo.repository.BoardRepository
import com.example.studymatetwo.room.entity.BoardEntity
import com.example.studymatetwo.util.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository
) : ViewModel() {

    private val _mutableBoardList = MutableLiveData<List<BoardDto>>()
    val boardList: LiveData<List<BoardDto>> get() = _mutableBoardList

    private val _mutableBoardContent = MutableLiveData<BoardDto>()
    val boardContent: LiveData<BoardDto> get() = _mutableBoardContent

    private val _mutableBoardComment = MutableLiveData<String>()
    val boardComment: LiveData<String> get() = _mutableBoardComment

    private val _mutableBoardSearchList = MutableLiveData<List<BoardDto>>()
    val boardSearchList: LiveData<List<BoardDto>> get() = _mutableBoardSearchList

    private val _mutableCommentList = MutableLiveData<List<CommentDto>>()
    val commentList: LiveData<List<CommentDto>> get() = _mutableCommentList

    private val _mutableErrorState = MutableLiveData<String>()
    val errorState: LiveData<String> get() = _mutableErrorState

    // 로컬 DB 데이터를 가져오는 함수도 인터페이스에 포함된 것을 사용합니다.
    val localBoardList: LiveData<List<BoardEntity>> = repository.getLocalBoardList()

    fun getBoardList(userToken: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getBoardList(userToken)
            .onFailure {
                _mutableErrorState.postValue(it.handleError())
            }
    }

    fun getBoardContent(userToken: String, boardId: String) = viewModelScope.launch {
        repository.getBoardContent(userToken, boardId)
            .onSuccess {
                _mutableBoardContent.postValue(it)
            }
            .onFailure {
                _mutableErrorState.postValue(it.handleError())
            }
    }

    fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto) = viewModelScope.launch {
        repository.postBoardComment(userToken, boardId, commentContentDto)
            .onSuccess {
                _mutableBoardComment.postValue(it)
            }
            .onFailure {
                _mutableErrorState.postValue(it.handleError())
            }
    }

    fun getCommentList(userToken: String, boardId: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getCommentList(userToken, boardId)
            .onSuccess {
                _mutableCommentList.postValue(it)
            }
            .onFailure {
                _mutableErrorState.postValue(it.handleError())
            }
    }

    fun getBoardSearch(userToken: String, keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getBoardSearch(userToken, keyword)
            .onSuccess {
                _mutableBoardSearchList.postValue(it)
            }
            .onFailure {
                _mutableErrorState.postValue(it.handleError())
            }
    }

}
