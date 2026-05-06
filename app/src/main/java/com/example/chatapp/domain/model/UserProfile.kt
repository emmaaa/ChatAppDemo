package com.example.chatapp.domain.model

import androidx.annotation.DrawableRes

// In a real app the avatar would be a url, and there would be a userID here etc...
data class UserProfile(
    val name: String,
    @param:DrawableRes val avatarRes: Int? = null
)
