package com.example.studymatetwo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.api.ApiResponse
import com.example.studymatetwo.dto.BoardDto
import com.example.studymatetwo.dto.CommentContentDto
import com.example.studymatetwo.dto.CommentDto
import com.example.studymatetwo.error.AppError
import com.example.studymatetwo.error.HttpError
import com.example.studymatetwo.error.MainResponse
import com.example.studymatetwo.repository.BoardRepository
import com.example.studymatetwo.repositoryImpl.BoardRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(private val repository: BoardRepositoryImpl) : ViewModel() {
    private var _mutableBoardList = MutableLiveData<List<BoardDto>>()
    val boardList: LiveData<List<BoardDto>> get() = _mutableBoardList

    private var _mutableBoardContent = MutableLiveData<BoardDto>()
    val boardContent: LiveData<BoardDto> get() = _mutableBoardContent

    private var _mutableBoardComment = MutableLiveData<String>()
    val boardComment: LiveData<String> get() = _mutableBoardComment

    private var _mutableBoardSearchList = MutableLiveData<List<BoardDto>>()
    val boardSearchList: LiveData<List<BoardDto>> get() = _mutableBoardSearchList

    private var _mutableCommentList = MutableLiveData<List<CommentDto>>()
    val commentList: LiveData<List<CommentDto>> get() = _mutableCommentList

    private var _mutableErrorState = MutableLiveData<String>()
    val errorState : LiveData<String> get() = _mutableErrorState


    fun getBoardList(userToken: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getBoardList(userToken)
            .onSuccess {
                _mutableBoardList.postValue(it)
            }
            .onFailure {
                _mutableErrorState.value = it.handleError()
            }
    }

    fun getBoardContent(userToken: String, boardId: String) = viewModelScope.launch {
        repository.getBoardContent(userToken, boardId)
            .onSuccess {
                _mutableBoardContent.postValue(it)
            }
            .onFailure {
                _mutableErrorState.value = it.handleError()
            }
    }

    fun postBoardComment(userToken: String, boardId: String, commentContentDto: CommentContentDto) = viewModelScope.launch {
        repository.postBoardComment(userToken,boardId, commentContentDto)
            .onSuccess {
                _mutableBoardComment.postValue(it)
            }
            .onFailure {
                _mutableErrorState.value = it.handleError()
            }
    }

    fun getCommentList(userToken: String, boardId: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getCommentList(userToken, boardId)
            .onSuccess {
                _mutableCommentList.postValue(it)
            }
            .onFailure {
                _mutableErrorState.value = it.handleError()
            }
    }

    fun getBoardSearch(userToken: String, keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getBoardSearch(userToken, keyword)
            .onSuccess {
                _mutableBoardSearchList.postValue(it)
            }
            .onFailure {
                _mutableErrorState.value = it.handleError()
            }
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