package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.model.UserProfile

data class ChatUiState(
    val items: List<ChatListItem> = emptyList(),
    val inputText: String = "",
    val isOtherTyping: Boolean = false,
    val otherUserProfile: UserProfile
)
