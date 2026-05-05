package com.example.chatapp.data.repository

import com.example.chatapp.data.local.MessageService
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageService: MessageService,
    private val messageMapper: MessageMapper
) : MessageRepository {

    override fun getMessages(): Flow<List<Message>> =
        messageService.getMessages().map { entities ->
            entities.map { messageMapper.toDomain(it) }
        }

    override suspend fun sendMessage(message: Message) =
        messageService.insertMessage(messageMapper.toEntity(message))
}
