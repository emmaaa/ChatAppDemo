package com.example.chatapp.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.chatapp.R
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.model.UserProfile
import com.example.chatapp.presentation.chat.ChatListItem.MessageItem
import com.example.chatapp.ui.theme.ChatDimensions

@Composable
internal fun MessageBubble(
    item: MessageItem,
    isCurrentUser: Boolean,
    otherUserProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = getRowPadding(item)),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isCurrentUser) {
            UserAvatar(
                profile = otherUserProfile,
                size = ChatDimensions.avatarSizeMessage,
                modifier = Modifier.padding(end = ChatDimensions.paddingAvatarEnd)
            )
        }

        Surface(
            shape = getChatBubbleShape(isCurrentUser),
            color = getBubbleColour(isCurrentUser),
            modifier = Modifier.widthIn(max = ChatDimensions.bubbleMaxWidth)
        ) {
            Text(
                text = item.message.text,
                color = getTextColour(isCurrentUser),
                fontSize = ChatDimensions.textSizeMessage,
                lineHeight = ChatDimensions.lineHeightMessage,
                modifier = Modifier.padding(
                    horizontal = ChatDimensions.paddingTextBubbleHorizontal,
                    vertical = ChatDimensions.paddingTextBubbleVertical
                )
            )
        }
    }
}

@Composable
private fun getTextColour(isCurrentUser: Boolean): Color =
    if (isCurrentUser) MaterialTheme.colorScheme.onPrimary
    else MaterialTheme.colorScheme.onSurfaceVariant

@Composable
private fun getBubbleColour(isCurrentUser: Boolean): Color =
    if (isCurrentUser) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.surfaceVariant

private fun getRowPadding(item: MessageItem): Dp =
    if (item.isSmallSpacingBelow) ChatDimensions.messageSpacingSmall
    else ChatDimensions.messageSpacingNormal

private fun getChatBubbleShape(isCurrentUser: Boolean): RoundedCornerShape = if (isCurrentUser) {
    // Sent (tail on bottom-right)
    RoundedCornerShape(
        topStart = ChatDimensions.bubbleCornerRadiusLarge,
        topEnd = ChatDimensions.bubbleCornerRadiusLarge,
        bottomStart = ChatDimensions.bubbleCornerRadiusLarge,
        bottomEnd = ChatDimensions.bubbleCornerRadiusTail
    )
} else {
    // Received (tail on bottom-left)
    RoundedCornerShape(
        topStart = ChatDimensions.bubbleCornerRadiusLarge,
        topEnd = ChatDimensions.bubbleCornerRadiusLarge,
        bottomStart = ChatDimensions.bubbleCornerRadiusTail,
        bottomEnd = ChatDimensions.bubbleCornerRadiusLarge
    )
}

@Preview(showBackground = true)
@Composable
private fun MessageBubbleSentPreview() {
    val item = MessageItem(
        message = Message(id = 1, senderId = "me", text = "Hey, how are you?", timestamp = 0L),
        isSmallSpacingBelow = false
    )
    MessageBubble(
        item = item,
        isCurrentUser = true,
        otherUserProfile = UserProfile(name = "Sarah", avatarRes = R.drawable.avatar)
    )
}

@Preview(showBackground = true)
@Composable
private fun MessageBubbleReceivedPreview() {
    val item = MessageItem(
        message = Message(
            id = 2,
            senderId = "sarah",
            text = "Not much, how are you?",
            timestamp = 0L
        ),
        isSmallSpacingBelow = false
    )
    MessageBubble(
        item = item,
        isCurrentUser = false,
        otherUserProfile = UserProfile(name = "Sarah", avatarRes = R.drawable.avatar)
    )
}
