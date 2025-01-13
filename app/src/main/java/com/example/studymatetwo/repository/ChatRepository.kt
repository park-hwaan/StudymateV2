package com.example.studymatetwo.repository

import com.example.studymatetwo.dto.ChatRoomDto

interface ChatRepository {
    suspend fun getChatRoom(userToken: String) : List<ChatRoomDto>
}