package com.example.chatapp.presentation.chat

data class ChatUiState(
    val items: List<ChatListItem> = emptyList(),
    val inputText: String = "",
    val isOtherTyping: Boolean = false
)
