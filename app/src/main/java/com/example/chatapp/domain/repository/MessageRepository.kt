package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

// Overengineered for this, I know! Only decoupling domain from Room for
// better testability in GetMessagesUseCase and SendMessageUseCase
interface MessageRepository {
    fun getMessages(): Flow<List<Message>>
    suspend fun sendMessage(message: Message)
}
