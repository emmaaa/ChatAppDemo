package com.example.chatapp.data.repository

import com.example.chatapp.data.local.MessageService
import com.example.chatapp.data.local.MessageEntity
import com.example.chatapp.domain.model.Message
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MessageRepositoryImplTest {

    private lateinit var dao: MessageService
    private lateinit var mapper: MessageMapper
    private lateinit var repository: MessageRepositoryImpl

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        mapper = MessageMapper()
        repository = MessageRepositoryImpl(dao, mapper)
    }

    @Test
    fun `getMessages should map entities to domain messages`() = runTest {
        val entity = MessageEntity(
            id = 1,
            senderId = "user1",
            text = "Hello",
            timestamp = 1000L
        )
        every { dao.getMessages() } returns flowOf(listOf(entity))

        val result = repository.getMessages().first()

        assertEquals(1, result.size)
        assertEquals("user1", result[0].senderId)
        assertEquals("Hello", result[0].text)
    }

    @Test
    fun `sendMessage should call dao insertMessage`() = runTest {
        val message = Message(
            id = 0,
            senderId = "user1",
            text = "Test",
            timestamp = 1000L
        )

        repository.sendMessage(message)

        coVerify {
            dao.insertMessage(match { entity ->
                entity.senderId == "user1" && entity.text == "Test"
            })
        }
    }


    @Test
    fun `getMessages should handle empty list`() = runTest {
        every { dao.getMessages() } returns flowOf(emptyList())

        val result = repository.getMessages().first()

        assertEquals(emptyList(), result)
    }
}

