package com.example.chatapp.domain.model

data class Message(
    val id: Long = 0,
    val senderId: String,
    val text: String,
    val timestamp: Long
)
