package com.example.chatapp.domain.time

interface TimeProvider {
    fun currentTimeMillis(): Long
}
