package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.model.Message

sealed class ChatListItem {
    data class SectionHeader(val id: String, val label: String) : ChatListItem()
    data class MessageItem(val message: Message, val isSmallSpacingBelow: Boolean) : ChatListItem()
}
