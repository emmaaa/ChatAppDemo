package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageRepository
import com.example.chatapp.domain.time.TimeProvider
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: MessageRepository,
    private val timeProvider: TimeProvider
) {
    suspend operator fun invoke(text: String, senderId: String) {
        val message = Message(
            senderId = senderId,
            text = text.trim(),
            timestamp = timeProvider.currentTimeMillis()
        )
        repository.sendMessage(message)
    }
}
