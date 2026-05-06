package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.time.TimeProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MessageTimeFormatter @Inject constructor(
    private val timeProvider: TimeProvider
) {
    fun formatSectionLabel(timestamp: Long): String {
        val today = Calendar.getInstance().apply {
            timeInMillis = timeProvider.currentTimeMillis()
        }
        val messageDay = Calendar.getInstance().apply { timeInMillis = timestamp }
        val yesterday = today.clone() as Calendar
        yesterday.add(Calendar.DAY_OF_YEAR, -1)

        val day = when {
            messageDay.isSameDay(today) -> "Today"
            messageDay.isSameDay(yesterday) -> "Yesterday"
            else -> SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(timestamp))
        }
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
        return "$day $time"
    }

    private fun Calendar.isSameDay(other: Calendar) =
        get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
}
