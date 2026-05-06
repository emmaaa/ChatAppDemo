package com.example.chatapp.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.Message
import com.example.chatapp.R
import com.example.chatapp.domain.model.UserProfile
import com.example.chatapp.domain.usecase.GetMessagesUseCase
import com.example.chatapp.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/** How old a previous message must be before a section header is shown (1 hour) */
private const val MESSAGE_SECTION_THRESHOLD = 60 * 60 * 1_000L

/** How long before we show the next message in a new bubble (20 seconds) */
private const val MESSAGE_SEPARATION_THRESHOLD = 20_000L

@HiltViewModel
class ChatViewModel @Inject constructor(
    getMessages: GetMessagesUseCase,
    private val sendMessage: SendMessageUseCase,
    private val messageTimeFormatter: MessageTimeFormatter
) : ViewModel() {

    private val _inputText = MutableStateFlow("")
    private val _otherTypingCount = MutableStateFlow(0)
    private var nextReplyIndex = 0

    val uiState: StateFlow<ChatUiState> = combine(
        getMessages(),
        _inputText,
        _otherTypingCount
    ) { messages, input, typingCount ->
        ChatUiState(
            items = messages.toListItems(),
            inputText = input,
            isOtherTyping = typingCount > 0,
            otherUserProfile = OTHER_USER_PROFILE
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ChatUiState(otherUserProfile = OTHER_USER_PROFILE)
    )

    fun onInputChanged(text: String) {
        _inputText.value = text
    }

    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isBlank()) return
        _inputText.value = ""
        viewModelScope.launch {
            sendMessage(text, CURRENT_USER_ID)
            simulateOtherTypingAndReply()
        }
    }

    private suspend fun simulateOtherTypingAndReply() {
        _otherTypingCount.value += 1
        try {
            delay(3_200)
            val reply = DEMO_REPLIES[nextReplyIndex % DEMO_REPLIES.size]
            nextReplyIndex++
            sendMessage(reply, OTHER_USER_ID)
        } finally {
            _otherTypingCount.value = (_otherTypingCount.value - 1).coerceAtLeast(0)
        }
    }

    /**
     * Converts the flat list of [Message]s into [ChatListItem]s by:
     *  1. Inserting [ChatListItem.SectionHeader] at the start, and where the time gap > 1 hour
     *  2. Grouping messages from the same sender sent within 20 seconds into a single MessageItem
     */
    private fun List<Message>.toListItems(): List<ChatListItem> {
        if (isEmpty()) return emptyList()

        val groupedMessages = groupConsecutiveMessages()
        return groupedMessages.flatMapIndexed { index, group ->
            val message = group.first()
            val previousGroup = groupedMessages.getOrNull(index - 1)
            val items = mutableListOf<ChatListItem>()

            if (shouldBuildSectionHeader(message.timestamp, previousGroup)) {
                items += buildSectionHeader(message.timestamp)
            }

            items += ChatListItem.MessageItem(
                group.combineIntoSingleMessage(),
                isSmallSpacingBelow = false
            )
            items
        }
    }

    private fun shouldBuildSectionHeader(
        messageTimestamp: Long,
        previousGroup: List<Message>?
    ): Boolean {
        val previousMessageTimestamp = previousGroup?.last()?.timestamp ?: return true
        val timeSincePreviousMessage = messageTimestamp - previousMessageTimestamp
        return timeSincePreviousMessage > MESSAGE_SECTION_THRESHOLD
    }

    private fun buildSectionHeader(messageTimestamp: Long): ChatListItem.SectionHeader =
        ChatListItem.SectionHeader(
            id = "header_$messageTimestamp",
            label = messageTimeFormatter.formatSectionLabel(messageTimestamp)
        )

    private fun isWithinMessageSeparationThreshold(
        currentTimestamp: Long,
        previousTimestamp: Long
    ): Boolean =
        currentTimestamp - previousTimestamp < MESSAGE_SEPARATION_THRESHOLD

    private fun shouldAppendToLastGroup(
        lastGroup: List<Message>,
        message: Message
    ): Boolean {
        val previousMessageTimestamp = lastGroup.last().timestamp
        return lastGroup.first().senderId == message.senderId &&
            isWithinMessageSeparationThreshold(message.timestamp, previousMessageTimestamp)
    }

    private fun List<Message>.groupConsecutiveMessages(): List<List<Message>> =
        fold(emptyList()) { groups, message ->
            val lastGroup = groups.lastOrNull()?.toMutableList()

            if (lastGroup != null && shouldAppendToLastGroup(lastGroup, message)) {
                // Add to existing group
                groups.dropLast(1) + listOf(lastGroup.apply { add(message) })
            } else {
                // Start new group
                groups + listOf(listOf(message))
            }
        }

    /**
     * Combines a group of messages into a single message with text joined by newlines.
     * If the group contains only one message, returns it unchanged.
     */
    private fun List<Message>.combineIntoSingleMessage(): Message =
        first().takeIf { size == 1 }
            ?: first().copy(text = joinToString("\n") { it.text })

    companion object {
        const val CURRENT_USER_ID = "me"
        const val OTHER_USER_ID = "sarah"
        val OTHER_USER_PROFILE = UserProfile(
            name = "Sarah",
            avatarRes = R.drawable.avatar
        )

        private val DEMO_REPLIES = listOf(
            "Hey, not much, how are you?",
            "Amazing, they have such good pizza!",
            "Sounds good to me, i'll grab my coat",
            "Perfect, let's do it.",
            "Cool, see you soon!"
        )
    }
}
