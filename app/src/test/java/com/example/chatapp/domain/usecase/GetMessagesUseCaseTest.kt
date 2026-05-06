package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetMessagesUseCaseTest {

    private lateinit var repository: MessageRepository
    private lateinit var useCase: GetMessagesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetMessagesUseCase(repository)
    }

    @Test
    fun `invoke should return flow from repository`() = runTest {
        val testMessages = listOf(
            Message(id = 1, senderId = "user1", text = "Hello", timestamp = 1000L),
            Message(id = 2, senderId = "user2", text = "Hi there", timestamp = 2000L)
        )
        every { repository.getMessages() } returns flowOf(testMessages)

        val result = useCase().first()

        assertEquals(testMessages, result)
    }

    @Test
    fun `invoke should return empty list initially`() = runTest {
        every { repository.getMessages() } returns flowOf(emptyList())

        val result = useCase().first()

        assertEquals(emptyList(), result)
    }
}
