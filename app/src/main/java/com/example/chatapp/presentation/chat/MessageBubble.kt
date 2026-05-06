package com.example.chatapp.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.chatapp.R
import com.example.chatapp.domain.model.Message
import com.example.chatapp.presentation.chat.ChatListItem.MessageItem
import com.example.chatapp.ui.theme.AvatarBackground
import com.example.chatapp.ui.theme.BubbleReceived
import com.example.chatapp.ui.theme.BubbleSent
import com.example.chatapp.ui.theme.ChatDimensions
import com.example.chatapp.ui.theme.TextReceived
import com.example.chatapp.ui.theme.TextSent

@Composable
internal fun MessageBubble(
    item: MessageItem,
    isCurrentUser: Boolean,
    otherUserName: String,
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
                name = otherUserName,
                size = ChatDimensions.avatarSizeMessage,
                backgroundColor = AvatarBackground,
                modifier = Modifier.padding(end = ChatDimensions.paddingAvatarEnd),
                imageResourceId = if (otherUserName == "Sarah") R.drawable.avatar else null
            )
        }

        Surface(
            shape = getChatBubbleShape(isCurrentUser),
            color = if (isCurrentUser) BubbleSent else BubbleReceived,
            modifier = Modifier.widthIn(max = ChatDimensions.bubbleMaxWidth)
        ) {
            Text(
                text = item.message.text,
                color = if (isCurrentUser) TextSent else TextReceived,
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

private fun getRowPadding(item: MessageItem): Dp =
    if (item.isSmallSpacingBelow) ChatDimensions.messageSpacingSmall else ChatDimensions.messageSpacingNormal

private fun getChatBubbleShape(isCurrentUser: Boolean): RoundedCornerShape = if (isCurrentUser) {
    // Sent: small tail on bottom-right
    RoundedCornerShape(
        topStart = ChatDimensions.bubbleCornerRadiusLarge,
        topEnd = ChatDimensions.bubbleCornerRadiusLarge,
        bottomStart = ChatDimensions.bubbleCornerRadiusLarge,
        bottomEnd = ChatDimensions.bubbleCornerRadiusTail
    )
} else {
    // Received: small tail on bottom-left
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
    MessageBubble(item = item, isCurrentUser = true, otherUserName = "Sarah")
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
    MessageBubble(item = item, isCurrentUser = false, otherUserName = "Sarah")
}
