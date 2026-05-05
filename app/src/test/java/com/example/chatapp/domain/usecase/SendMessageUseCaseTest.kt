package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.MessageRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SendMessageUseCaseTest {

    private lateinit var repository: MessageRepository
    private lateinit var useCase: SendMessageUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SendMessageUseCase(repository)
    }

    @Test
    fun `invoke should call repository sendMessage with correct text and senderId`() = runTest {
        val text = "Hello, World!"
        val senderId = "user123"

        useCase(text, senderId)

        coVerify {
            repository.sendMessage(match { message ->
                message.text == text && message.senderId == senderId
            })
        }
    }

    @Test
    fun `invoke should trim whitespace from text`() = runTest {
        val text = "  Hello, World!  "
        val senderId = "user123"

        useCase(text, senderId)

        coVerify {
            repository.sendMessage(match { message ->
                message.text == "Hello, World!"
            })
        }
    }

    @Test
    fun `invoke should use current time as message timestamp`() = runTest {
        val text = "Test message"
        val senderId = "user123"
        val timeBefore = System.currentTimeMillis()

        useCase(text, senderId)

        val timeAfter = System.currentTimeMillis()

        coVerify {
            repository.sendMessage(match { message ->
                message.timestamp in timeBefore..timeAfter
            })
        }
    }
}

