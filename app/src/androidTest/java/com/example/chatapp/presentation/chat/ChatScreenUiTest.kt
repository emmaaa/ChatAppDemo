package com.example.chatapp.presentation.chat

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.chatapp.domain.time.TimeProvider
import com.example.chatapp.domain.usecase.GetMessagesUseCase
import com.example.chatapp.domain.usecase.SendMessageUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ChatScreenUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun simple_first_UI_test() {
        // Given
        val getMessagesUseCase = mockk<GetMessagesUseCase>()
        val sendMessageUseCase = mockk<SendMessageUseCase>()
        every { getMessagesUseCase() } returns flowOf(emptyList())
        val formatter = MessageTimeFormatter(object : TimeProvider {
            override fun currentTimeMillis() = System.currentTimeMillis()
        })
        val viewModel = ChatViewModel(getMessagesUseCase, sendMessageUseCase, formatter)

        // When
        composeTestRule.setContent {
            ChatScreen(viewModel)
        }

        // Then
        composeTestRule
            .onNodeWithText("Type a message…")
            .assertIsDisplayed()
    }
}
