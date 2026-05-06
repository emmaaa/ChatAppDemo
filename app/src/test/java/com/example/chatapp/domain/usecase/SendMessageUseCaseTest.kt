package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.MessageRepository
import com.example.chatapp.domain.time.TimeProvider
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SendMessageUseCaseTest {

    private lateinit var repository: MessageRepository
    private lateinit var timeProvider: FakeTimeProvider
    private lateinit var useCase: SendMessageUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        timeProvider = FakeTimeProvider(now = 1_234L)
        useCase = SendMessageUseCase(repository, timeProvider)
    }

    @Test
    fun `Given valid text and senderId, When invoke, Then repository receives message`() = runTest {
        // Given
        val text = "Hello, World!"
        val senderId = "user123"

        // When
        useCase(text, senderId)

        // Then
        coVerify {
            repository.sendMessage(match { message ->
                message.text == text && message.senderId == senderId
            })
        }
    }

    @Test
    fun `Given surrounding whitespace, When invoke, Then text is trimmed`() = runTest {
        // Given
        val text = "  Hello, World!  "
        val senderId = "user123"

        // When
        useCase(text, senderId)

        // Then
        coVerify {
            repository.sendMessage(match { message ->
                message.text == "Hello, World!"
            })
        }
    }

    @Test
    fun `Given a message, When invoke, Then timestamp comes from TimeProvider`() = runTest {
        // Given
        val text = "Test message"
        val senderId = "user123"

        // When
        useCase(text, senderId)

        // Then
        coVerify {
            repository.sendMessage(match { message ->
                message.timestamp == 1_234L
            })
        }
    }

    private class FakeTimeProvider(private val now: Long) : TimeProvider {
        override fun currentTimeMillis(): Long = now
    }
}
