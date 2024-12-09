package com.example.studymatetwo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymatetwo.dto.ChatRoomDto
import com.example.studymatetwo.dto.MentorDto
import com.example.studymatetwo.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private var _mutableChatRoomList = MutableLiveData<List<ChatRoomDto>>(emptyList())
    val chatRoomList: LiveData<List<ChatRoomDto>> get() = _mutableChatRoomList

    fun getMentorList(userToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getChatRoom(userToken)
            _mutableChatRoomList.postValue(response)
        }
    }
}