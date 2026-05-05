package com.example.chatapp.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.domain.usecase.GetMessagesUseCase
import com.example.chatapp.domain.usecase.SendMessageUseCase

/**
 * Manual ViewModelFactory – wires dependencies without a DI framework,
 * keeping the setup explicit and easy to follow in an interview context.
 */
class ChatViewModelFactory(
    private val getMessages: GetMessagesUseCase,
    private val sendMessage: SendMessageUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == ChatViewModel::class.java)
        return ChatViewModel(getMessages, sendMessage) as T
    }
}

