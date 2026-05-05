package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: MessageRepository) {
    suspend operator fun invoke(text: String, senderId: String) {
        val message = Message(
            senderId = senderId,
            text = text.trim(),
            timestamp = System.currentTimeMillis()
        )
        repository.sendMessage(message)
    }
}
