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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.domain.model.Message
import com.example.chatapp.ui.theme.BubbleReceived
import com.example.chatapp.ui.theme.BubbleSent

@Composable
internal fun MessageBubble(
    item: ChatListItem.MessageItem,
    isCurrentUser: Boolean,
    otherUserName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                bottom = if (item.isSmallSpacingBelow)
                    dimensionResource(R.dimen.message_spacing_small)
                else
                    dimensionResource(R.dimen.message_spacing_normal)
            ),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isCurrentUser) {
            UserAvatar(
                name = otherUserName,
                size = dimensionResource(R.dimen.avatar_size_message),
                backgroundColor = colorResource(R.color.avatar_background),
                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_avatar_end)),
                imageResourceId = if (otherUserName == "Sarah") R.drawable.avatar else null
            )
        }

        val bubbleShape = if (isCurrentUser) {
            // Sent: small tail on bottom-right
            RoundedCornerShape(
                topStart = dimensionResource(R.dimen.bubble_corner_radius_large),
                topEnd = dimensionResource(R.dimen.bubble_corner_radius_large),
                bottomStart = dimensionResource(R.dimen.bubble_corner_radius_large),
                bottomEnd = dimensionResource(R.dimen.bubble_corner_radius_tail)
            )
        } else {
            // Received: small tail on bottom-left
            RoundedCornerShape(
                topStart = dimensionResource(R.dimen.bubble_corner_radius_large),
                topEnd = dimensionResource(R.dimen.bubble_corner_radius_large),
                bottomStart = dimensionResource(R.dimen.bubble_corner_radius_tail),
                bottomEnd = dimensionResource(R.dimen.bubble_corner_radius_large)
            )
        }

        Surface(
            shape = bubbleShape,
            color = if (isCurrentUser) BubbleSent else BubbleReceived,
            modifier = Modifier.widthIn(max = dimensionResource(R.dimen.bubble_max_width))
        ) {
            Text(
                text = item.message.text,
                color = if (isCurrentUser) Color.White else colorResource(R.color.message_text_received),
                fontSize = dimensionResource(R.dimen.text_size_message).value.sp,
                lineHeight = dimensionResource(R.dimen.line_height_message).value.sp,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_text_bubble_horizontal),
                    vertical = dimensionResource(R.dimen.padding_text_bubble_vertical)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageBubbleSentPreview() {
    val item = ChatListItem.MessageItem(
        message = Message(id = 1, senderId = "me", text = "Hey, how are you?", timestamp = 0L),
        isSmallSpacingBelow = false
    )
    MessageBubble(item = item, isCurrentUser = true, otherUserName = "Sarah")
}

@Preview(showBackground = true)
@Composable
private fun MessageBubbleReceivedPreview() {
    val item = ChatListItem.MessageItem(
        message = Message(id = 2, senderId = "sarah", text = "Not much, how are you?", timestamp = 0L),
        isSmallSpacingBelow = false
    )
    MessageBubble(item = item, isCurrentUser = false, otherUserName = "Sarah")
}
