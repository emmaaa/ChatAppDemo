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
    fun `Given repository has messages, When invoked, Then returns those messages`() = runTest {
        // Given
        val testMessages = listOf(
            Message(id = 1, senderId = "user1", text = "Hello", timestamp = 1000L),
            Message(id = 2, senderId = "user2", text = "Hi there", timestamp = 2000L)
        )
        every { repository.getMessages() } returns flowOf(testMessages)

        // When
        val result = useCase().first()

        // Then
        assertEquals(testMessages, result)
    }

    @Test
    fun `Given repository has no messages, When invoked, Then returns empty list`() = runTest {
        // Given
        every { repository.getMessages() } returns flowOf(emptyList())

        // When
        val result = useCase().first()

        // Then
        assertEquals(emptyList(), result)
    }
}
