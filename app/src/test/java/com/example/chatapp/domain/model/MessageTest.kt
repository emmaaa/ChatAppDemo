package com.example.chatapp.domain.model

import org.junit.Test
import kotlin.test.assertEquals

class MessageTest {

    @Test
    fun messageCreationWithAllFields() {
        val message = Message(
            id = 1L,
            senderId = "user1",
            text = "Hello",
            timestamp = 1000L
        )

        assertEquals(1L, message.id)
        assertEquals("user1", message.senderId)
        assertEquals("Hello", message.text)
        assertEquals(1000L, message.timestamp)
    }

    @Test
    fun messageCreationWithDefaultId() {
        val message = Message(
            senderId = "user1",
            text = "Hello",
            timestamp = 1000L
        )

        assertEquals(0L, message.id)
        assertEquals("user1", message.senderId)
    }

    @Test
    fun messageDataClass() {
        val msg1 = Message(id = 1, senderId = "user1", text = "Hello", timestamp = 1000L)
        val msg2 = Message(id = 1, senderId = "user1", text = "Hello", timestamp = 1000L)

        // Data class equality
        assertEquals(msg1, msg2)
    }

    @Test
    fun messageCopy() {
        val original = Message(id = 1, senderId = "user1", text = "Hello", timestamp = 1000L)
        val copy = original.copy(text = "Hi")

        assertEquals("Hi", copy.text)
        assertEquals("user1", copy.senderId)
        assertEquals(1L, copy.id)
    }
}

