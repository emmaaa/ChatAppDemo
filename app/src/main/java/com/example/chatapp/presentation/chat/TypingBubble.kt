package com.example.chatapp.presentation.chat

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.R
import com.example.chatapp.ui.theme.AvatarBackground
import com.example.chatapp.ui.theme.BubbleReceived
import com.example.chatapp.ui.theme.ChatDimensions
import com.example.chatapp.ui.theme.TypingIndicatorText

@Composable
internal fun TypingBubble(
    userName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = ChatDimensions.messageSpacingNormal),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        UserAvatar(
            name = userName,
            size = ChatDimensions.avatarSizeMessage,
            backgroundColor = AvatarBackground,
            modifier = Modifier.padding(end = ChatDimensions.paddingAvatarEnd),
            imageResourceId = if (userName == "Sarah") R.drawable.avatar else null
        )

        Surface(
            shape = RoundedCornerShape(
                topStart = ChatDimensions.bubbleCornerRadiusLarge,
                topEnd = ChatDimensions.bubbleCornerRadiusLarge,
                bottomStart = ChatDimensions.bubbleCornerRadiusTail,
                bottomEnd = ChatDimensions.bubbleCornerRadiusLarge
            ),
            color = BubbleReceived,
            modifier = Modifier.widthIn(max = ChatDimensions.typingBubbleMaxWidth)
        ) {
            Text(
                text = stringResource(R.string.typing_indicator),
                color = TypingIndicatorText,
                fontSize = ChatDimensions.textSizeTyping,
                modifier = Modifier.padding(
                    horizontal = ChatDimensions.paddingTextBubbleHorizontal,
                    vertical = ChatDimensions.paddingTextBubbleVertical
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TypingBubblePreview() {
    TypingBubble(userName = "Sarah")
}
