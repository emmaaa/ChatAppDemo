package com.example.chatapp.data.repository

import com.example.chatapp.data.local.MessageEntity
import com.example.chatapp.domain.model.Message
import javax.inject.Inject

class MessageMapper @Inject constructor() {

    fun toDomain(entity: MessageEntity): Message =
        Message(id = entity.id, senderId = entity.senderId, text = entity.text, timestamp = entity.timestamp)

    fun toEntity(message: Message): MessageEntity =
        MessageEntity(id = message.id, senderId = message.senderId, text = message.text, timestamp = message.timestamp)
}
