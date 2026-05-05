package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.usecase.GetMessagesUseCase
import com.example.chatapp.domain.usecase.SendMessageUseCase
import com.example.chatapp.presentation.chat.ChatListItem.MessageItem
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private lateinit var getMessagesUseCase: GetMessagesUseCase
    private lateinit var sendMessageUseCase: SendMessageUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMessagesUseCase = mockk()
        sendMessageUseCase = mockk()
        coEvery { sendMessageUseCase(any(), any()) } just Runs
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given rapid messages, When uiState built, Then group into single item`() = runTest {
        // Given
        val messages = listOf(
            Message(id = 1, senderId = "me", text = "Hi", timestamp = 1_000L),
            Message(id = 2, senderId = "me", text = "How are you?", timestamp = 10_000L)
        )
        every { getMessagesUseCase() } returns flowOf(messages)

        // When
        val viewModel = ChatViewModel(getMessagesUseCase, sendMessageUseCase)
        val state = viewModel.uiState.drop(1).first()

        // Then
        assertEquals(2, state.items.size)
        assertEquals(
            MessageItem(
                message = Message(
                    id = 1,
                    senderId = "me",
                    text = "Hi\nHow are you?",
                    timestamp = 1_000L
                ),
                isSmallSpacingBelow = false
            ),
            state.items[1]
        )
    }

    @Test
    fun `Given messages at twenty seconds, When uiState built, Then not grouped`() = runTest {
        // Given
        val messages = listOf(
            Message(id = 1, senderId = "me", text = "First", timestamp = 1_000L),
            Message(id = 2, senderId = "me", text = "Second", timestamp = 21_000L)
        )
        every { getMessagesUseCase() } returns flowOf(messages)

        // When
        val viewModel = ChatViewModel(getMessagesUseCase, sendMessageUseCase)
        val state = viewModel.uiState.drop(1).first()

        // Then
        val messageItems = state.items.filterIsInstance<MessageItem>()
        assertEquals(2, messageItems.size)
        assertEquals("First", messageItems[0].message.text)
        assertEquals("Second", messageItems[1].message.text)
    }

    @Test
    fun `Given message gap over one hour, When uiState built, Then add section header`() = runTest {
        // Given
        val messages = listOf(
            Message(id = 1, senderId = "me", text = "Morning", timestamp = 1_000L),
            Message(id = 2, senderId = "sarah", text = "Evening", timestamp = 3_700_001L)
        )
        every { getMessagesUseCase() } returns flowOf(messages)

        // When
        val viewModel = ChatViewModel(getMessagesUseCase, sendMessageUseCase)
        val state = viewModel.uiState.drop(1).first()

        // Then
        val headers = state.items.filterIsInstance<ChatListItem.SectionHeader>()
        assertEquals(2, headers.size)
    }

    @Test
    fun `Given blank input, When sendMessage, Then send use case not invoked`() = runTest {
        // Given
        every { getMessagesUseCase() } returns flowOf(emptyList())
        val viewModel = ChatViewModel(getMessagesUseCase, sendMessageUseCase)

        // When
        viewModel.onInputChanged("   ")
        viewModel.sendMessage()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 0) { sendMessageUseCase(any(), any()) }
    }
}