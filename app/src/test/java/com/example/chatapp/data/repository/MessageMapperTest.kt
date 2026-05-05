package com.example.chatapp.data.repository

import com.example.chatapp.data.local.MessageEntity
import com.example.chatapp.domain.model.Message
import org.junit.Test
import kotlin.test.assertEquals

class MessageMapperTest {

    private val mapper = MessageMapper()

    @Test
    fun `Given Entity, When mapped, Then converts to Domain`() {
        val entity = MessageEntity(
            id = 1,
            senderId = "Woody",
            text = "Howdy",
            timestamp = 1000L
        )

        val result = mapper.toDomain(entity)

        assertEquals(1L, result.id)
        assertEquals("Woody", result.senderId)
        assertEquals("Howdy", result.text)
        assertEquals(1000L, result.timestamp)
    }

    @Test
    fun `Given Domain, When mapped, Then converts to Entity`() {
        val message = Message(
            id = 2,
            senderId = "Jessie",
            text = "Yeehaw",
            timestamp = 2000L
        )

        val result = mapper.toEntity(message)

        assertEquals(2L, result.id)
        assertEquals("Jessie", result.senderId)
        assertEquals("Yeehaw", result.text)
        assertEquals(2000L, result.timestamp)
    }
}